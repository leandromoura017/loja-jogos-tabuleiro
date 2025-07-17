package com.loja.jogostabuleiro.controller;

import com.loja.jogostabuleiro.model.JogoTabuleiro;
import com.loja.jogostabuleiro.service.JogoTabuleiroService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class JogoTabuleiroController {

    @Autowired
    private JogoTabuleiroService service;

    // Diretório para upload de imagens (configurável via application.properties)
    @Value("${app.upload.dir:${user.home}/uploads/jogos}")
    private String uploadDir;

    // Rota 3: Página inicial com jogos não deletados
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        List<JogoTabuleiro> jogos = service.findAllNotDeleted();
        model.addAttribute("jogos", jogos);
        model.addAttribute("title", "Loja de Jogos de Tabuleiro");

        // Log para debug das imagens
        System.out.println("=== JOGOS CARREGADOS NO INDEX ===");
        for (JogoTabuleiro jogo : jogos) {
            System.out.println("Jogo: " + jogo.getNome() + " - URL da imagem: " + jogo.getImgUrl());
        }

        // Adicionar quantidade de itens no carrinho
        List<JogoTabuleiro> carrinho = (List<JogoTabuleiro>) session.getAttribute("carrinho");
        int quantidadeCarrinho = carrinho != null ? carrinho.size() : 0;
        model.addAttribute("quantidadeCarrinho", quantidadeCarrinho);

        return "index";
    }

    // Rota 4: Página de administração com todos os jogos
    @GetMapping("/admin")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public String admin(Model model) {
        List<JogoTabuleiro> jogos = service.findAll();
        model.addAttribute("jogos", jogos);
        model.addAttribute("title", "Administração - Jogos de Tabuleiro");

        // Log para debug das imagens
        System.out.println("=== JOGOS CARREGADOS NO ADMIN ===");
        for (JogoTabuleiro jogo : jogos) {
            System.out.println("Jogo: " + jogo.getNome() + " - URL da imagem: " + jogo.getImgUrl());
        }

        return "admin";
    }

    // Rota 5: Formulário de cadastro
    @GetMapping("/cadastro")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public String cadastro(Model model) {
        model.addAttribute("jogo", new JogoTabuleiro());
        model.addAttribute("title", "Cadastrar Jogo");
        return "cadastro";
    }

    // Rota 6: Formulário de edição
    @GetMapping("/editar")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public String editar(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<JogoTabuleiro> jogoOpt = service.findById(id);
        if (jogoOpt.isPresent()) {
            JogoTabuleiro jogo = jogoOpt.get();
            System.out.println("=== EDITANDO JOGO ===");
            System.out.println("Jogo: " + jogo.getNome() + " - URL da imagem: " + jogo.getImgUrl());

            model.addAttribute("jogo", jogo);
            model.addAttribute("title", "Editar Jogo");
            return "cadastro";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Jogo não encontrado!");
            return "redirect:/admin";
        }
    }

    // Método auxiliar para fazer upload da imagem
    private String uploadImagem(MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            return null;
        }

        // Verificar tipo de arquivo
        String contentType = arquivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Arquivo deve ser uma imagem");
        }

        // Criar diretório se não existir
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Gerar nome único para o arquivo
        String extensao = "";
        String nomeOriginal = arquivo.getOriginalFilename();
        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        }

        String nomeArquivo = UUID.randomUUID().toString() + extensao;
        Path caminhoArquivo = Paths.get(uploadDir, nomeArquivo);

        // Salvar arquivo
        Files.copy(arquivo.getInputStream(), caminhoArquivo);

        // Retornar URL relativa
        return "/uploads/jogos/" + nomeArquivo;
    }

    // Rota 7: Salvar jogo (cadastro e edição)
    @PostMapping("/salvar")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public String salvar(@Valid @ModelAttribute("jogo") JogoTabuleiro jogo,
                         BindingResult result,
                         @RequestParam(value = "imagemArquivo", required = false) MultipartFile imagemArquivo,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        // Log para debug
        System.out.println("=== INÍCIO DO SALVAMENTO ===");
        System.out.println("Tentando salvar jogo: " + jogo.getNome());
        System.out.println("ID do jogo: " + jogo.getId());
        System.out.println("URL da imagem recebida: " + jogo.getImgUrl());
        System.out.println("Arquivo de imagem enviado: " + (imagemArquivo != null && !imagemArquivo.isEmpty()));
        System.out.println("Preço: " + jogo.getPreco());
        System.out.println("Categoria: " + jogo.getCategoria());
        System.out.println("Tem erros de validação: " + result.hasErrors());

        if (result.hasErrors()) {
            System.out.println("=== ERROS DE VALIDAÇÃO ENCONTRADOS ===");
            result.getAllErrors().forEach(error -> {
                System.out.println("Erro: " + error.getDefaultMessage());
            });

            model.addAttribute("title", jogo.getId() != null ? "Editar Jogo" : "Cadastrar Jogo");
            model.addAttribute("jogo", jogo);
            model.addAttribute("erro", "Por favor, corrija os erros abaixo e tente novamente.");
            return "cadastro";
        }

        try {
            // Validações adicionais de negócio
            if (jogo.getNome() == null || jogo.getNome().trim().isEmpty()) {
                System.out.println("Erro: Nome vazio");
                model.addAttribute("erro", "Nome do jogo é obrigatório!");
                model.addAttribute("title", jogo.getId() != null ? "Editar Jogo" : "Cadastrar Jogo");
                model.addAttribute("jogo", jogo);
                return "cadastro";
            }

            if (jogo.getPreco() == null) {
                System.out.println("Erro: Preço inválido - " + jogo.getPreco());
                model.addAttribute("erro", "Preço deve ser maior que zero!");
                model.addAttribute("title", jogo.getId() != null ? "Editar Jogo" : "Cadastrar Jogo");
                model.addAttribute("jogo", jogo);
                return "cadastro";
            }

            // Configurar campos padrão para novos jogos
            if (jogo.getId() == null) {
                jogo.setDataCadastro(LocalDate.from(LocalDateTime.now()));
                jogo.setDeleted(false);
            }

            // Tratar upload de imagem
            if (imagemArquivo != null && !imagemArquivo.isEmpty()) {
                try {
                    String urlImagem = uploadImagem(imagemArquivo);
                    if (urlImagem != null) {
                        jogo.setImgUrl(urlImagem);
                        System.out.println("Imagem enviada com sucesso: " + urlImagem);
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao fazer upload da imagem: " + e.getMessage());
                    model.addAttribute("erro", "Erro ao fazer upload da imagem: " + e.getMessage());
                    model.addAttribute("title", jogo.getId() != null ? "Editar Jogo" : "Cadastrar Jogo");
                    model.addAttribute("jogo", jogo);
                    return "cadastro";
                }
            } else {
                // Tratar URL da imagem externa
                if (jogo.getImgUrl() == null || jogo.getImgUrl().trim().isEmpty()) {
                    System.out.println("URL da imagem vazia, definindo padrão");
                    // Usar serviços confiáveis para placeholder
                    jogo.setImgUrl("https://picsum.photos/300/200?random=" + System.currentTimeMillis());
                } else {
                    // Limpar espaços em branco e validar URL
                    String url = jogo.getImgUrl().trim();

                    // Verificar se é uma URL válida
                    if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("/")) {
                        url = "https://" + url;
                    }

                    jogo.setImgUrl(url);
                    System.out.println("URL da imagem definida: " + jogo.getImgUrl());
                }
            }

            System.out.println("=== DADOS ANTES DO SALVAMENTO ===");
            System.out.println("Nome: " + jogo.getNome());
            System.out.println("Descrição: " + jogo.getDescricao());
            System.out.println("Preço: " + jogo.getPreco());
            System.out.println("Categoria: " + jogo.getCategoria());
            System.out.println("Idade Mínima: " + jogo.getIdadeMinima());
            System.out.println("Número de Jogadores: " + jogo.getNumeroJogadores());
            System.out.println("Tempo de Jogo: " + jogo.getTempoJogoMinutos());
            System.out.println("URL da Imagem: " + jogo.getImgUrl());
            System.out.println("Data Cadastro: " + jogo.getDataCadastro());
            System.out.println("Deleted: " + jogo.isDeleted());

            // Salvar o jogo
            JogoTabuleiro jogoSalvo = service.save(jogo);

            System.out.println("=== JOGO SALVO COM SUCESSO ===");
            System.out.println("ID gerado: " + jogoSalvo.getId());
            System.out.println("Nome salvo: " + jogoSalvo.getNome());
            System.out.println("URL da imagem salva: " + jogoSalvo.getImgUrl());

            String mensagem = (jogo.getId() == null) ?
                    "Jogo \'" + jogoSalvo.getNome() + "\' cadastrado com sucesso!" :
                    "Jogo \'" + jogoSalvo.getNome() + "\' atualizado com sucesso!";

            redirectAttributes.addFlashAttribute("sucesso", mensagem);

        } catch (Exception e) {
            System.err.println("=== ERRO AO SALVAR JOGO ===");
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();

            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar jogo: " + e.getMessage());
        }

        System.out.println("=== REDIRECIONANDO PARA /admin ===");
        return "redirect:/admin";
    }

    // Rota 8: Deletar jogo (soft delete)
    @GetMapping("/deletar")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public String deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<JogoTabuleiro> jogoOpt = service.findById(id);
            if (jogoOpt.isPresent()) {
                service.deleteById(id);
                redirectAttributes.addFlashAttribute("sucesso", "Jogo \'" + jogoOpt.get().getNome() + "\' removido com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Jogo não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao deletar jogo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover jogo: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    // Rota 9: Restaurar jogo
    @GetMapping("/restaurar")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public String restaurar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<JogoTabuleiro> jogoOpt = service.findById(id);
            if (jogoOpt.isPresent()) {
                service.restoreById(id);
                redirectAttributes.addFlashAttribute("sucesso", "Jogo \'" + jogoOpt.get().getNome() + "\' restaurado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Jogo não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao restaurar jogo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("erro", "Erro ao restaurar jogo: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    // Rota 10: Adicionar ao carrinho
    @GetMapping("/adicionarCarrinho")
    @PreAuthorize("hasAnyRole(\'USER\', \'ADMIN\')")
    public String adicionarCarrinho(@RequestParam("id") Long id,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        Optional<JogoTabuleiro> jogoOpt = service.findById(id);

        if (jogoOpt.isPresent()) {
            JogoTabuleiro jogo = jogoOpt.get();

            // Verificar se o jogo não está deletado
            if (!jogo.isDeleted()) {
                List<JogoTabuleiro> carrinho = (List<JogoTabuleiro>) session.getAttribute("carrinho");
                if (carrinho == null) {
                    carrinho = new ArrayList<>();
                }
                carrinho.add(jogo);
                session.setAttribute("carrinho", carrinho);
                redirectAttributes.addFlashAttribute("sucesso", "Jogo \'" + jogo.getNome() + "\' adicionado ao carrinho!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Este jogo não está disponível!");
            }
        } else {
            redirectAttributes.addFlashAttribute("erro", "Jogo não encontrado!");
        }

        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }

    // Rota 11: Ver carrinho
    @GetMapping("/carrinho")
    @PreAuthorize("hasAnyRole(\'USER\', \'ADMIN\')")
    public String verCarrinho(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        List<JogoTabuleiro> carrinho = (List<JogoTabuleiro>) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("info", "Não existem itens no carrinho.");
            return "redirect:/index";
        }

        model.addAttribute("carrinho", carrinho);
        model.addAttribute("title", "Carrinho de Compras");
        return "carrinho";
    }

    // Rota 12: Finalizar compra
    @GetMapping("/finalizarCompra")
    @PreAuthorize("hasAnyRole(\'USER\', \'ADMIN\')")
    public String finalizarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        List<JogoTabuleiro> carrinho = (List<JogoTabuleiro>) session.getAttribute("carrinho");

        if (carrinho != null && !carrinho.isEmpty()) {
            session.invalidate();
            redirectAttributes.addFlashAttribute("sucesso", "Compra finalizada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Carrinho vazio! Adicione itens antes de finalizar a compra.");
        }

        return "redirect:/index";
    }

    // Rota raiz redirecionando para index
    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }
}