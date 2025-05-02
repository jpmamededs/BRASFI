package br.com.brasfi.BRASFI.Service;



import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import br.com.brasfi.BRASFI.Repository.PostagemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PostagemService {

    @Autowired
    PostagemRepository postagemRepository;




    public List<Postagem> findAll() {


        return postagemRepository.findAll();

    }



    public List<Postagem> listarPostagensPorTag(TipoPostagem tag){

        return postagemRepository.findAllByTag(tag);
    }

    public List<Postagem> listarPostagensFixadas(){

        return postagemRepository.findAllByFixadoTrue();
    }

    public Postagem buscarPorId(Long id) {
        return postagemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada com o ID: " + id));
    }

    public void  criarPostagem(Postagem postagem){

        postagemRepository.save(postagem);




    }

    @Transactional
    public void editarPostagem(Long id, Postagem novaPostagem) {
        buscarPorId(id);
        postagemRepository.atualizarPostagem(id, novaPostagem);
    }


    public void deletarPostagem(Long id){

        postagemRepository.deleteById(id);
    }

    private boolean validarLimitePalavras(String texto){

        if (texto.length()> Postagem.LIMITE_PALAVRAS){

            return false;


        }
        else{
            return true;
        }
    }

    public void fixarPostagem(Long id) {
        Postagem postagemRecuperada = buscarPorId(id);

        if (!postagemRecuperada.isFixado()) {
            postagemRecuperada.setFixado(true);
            postagemRepository.save(postagemRecuperada); // n sei se precisa salvar ja aqui dps tem q rever
        }
    }


    public void desfixarPostagem(Long id) {
        Postagem postagemRecuperada = buscarPorId(id);

        if (postagemRecuperada.isFixado()) {
            postagemRecuperada.setFixado(false);
            postagemRepository.save(postagemRecuperada); // n sei se precisa salvar ja aqui dps tem q rever
        }
    }

}

