package br.com.brasfi.BRASFI.Service;

import br.com.brasfi.BRASFI.Model.*;
import br.com.brasfi.BRASFI.Model.enums.AreaDoConhecimento;
import br.com.brasfi.BRASFI.Repository.*;
import br.com.brasfi.BRASFI.dto.CriarCursoDTO;
import br.com.brasfi.BRASFI.dto.CursoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<CursoResponseDTO> listarTodosCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(CursoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO buscarCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .map(CursoResponseDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
    }

    public User buscarUsuarioAutenticado(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }



    @Transactional
    public CursoResponseDTO criarCursoCompleto(CriarCursoDTO dto, User autor) {

        if (autor == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }

        if (dto.getModulos() == null || dto.getModulos().isEmpty()) {
            throw new IllegalArgumentException("O curso deve conter pelo menos um módulo");
        }


        Curso curso = dto.toEntity();
        curso.setAutor(autor);
        Curso cursoSalvo = cursoRepository.save(curso);


        List<Modulo> modulosParaSalvar = new ArrayList<>();
        List<Aula> aulasParaSalvar = new ArrayList<>();
        List<MaterialApoio> materiaisParaSalvar = new ArrayList<>();

        dto.getModulos().forEach(moduloDto -> {
            Modulo modulo = moduloDto.toEntity(cursoSalvo);
            modulosParaSalvar.add(modulo);

            if (moduloDto.getAulas() != null) {
                moduloDto.getAulas().forEach(aulaDto -> {
                    Aula aula = aulaDto.toEntity(modulo);
                    aulasParaSalvar.add(aula);

                    if (aulaDto.getMateriais() != null) {
                        aulaDto.getMateriais().forEach(materialDto -> {
                            MaterialApoio material = materialDto.toEntity(aula);
                            materiaisParaSalvar.add(material);
                            aula.getMateriais().add(material);
                        });
                    }
                });
            }
        });


        moduloRepository.saveAll(modulosParaSalvar);
        aulaRepository.saveAll(aulasParaSalvar);
        materialRepository.saveAll(materiaisParaSalvar);


        cursoSalvo.setModulos(modulosParaSalvar);
        modulosParaSalvar.forEach(modulo -> {
            modulo.setAulas(aulasParaSalvar.stream()
                    .filter(aula -> aula.getModulo().getId().equals(modulo.getId()))
                    .collect(Collectors.toList()));
        });


        return CursoResponseDTO.fromEntity(cursoSalvo);
    }
}