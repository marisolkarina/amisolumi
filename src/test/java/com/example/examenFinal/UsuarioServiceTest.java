package com.example.examenFinal;

import com.example.examenFinal.application.UsuarioService;
import com.example.examenFinal.domain.model.Usuario;
import com.example.examenFinal.domain.ports.out.ReniecPortOut;
import com.example.examenFinal.domain.ports.out.UsuarioPortOut;
import com.example.examenFinal.insfraestructure.dto.response.ReniecResponse;
import com.example.examenFinal.insfraestructure.exception.DuplicateResourceException;
import com.example.examenFinal.insfraestructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioPortOut usuarioPortOut;

    @Mock
    private ReniecPortOut reniecPortOut;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Usuario usuarioActual;
    private UUID usuarioId;
    private ReniecResponse reniecResponse;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.fromString("123e4567-e89b-12d3-a456-426614174700");

        usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setNombres(null);
        usuario.setApellidos(null);
        usuario.setEmail("marisol@gmail.com");
        usuario.setPassword("123456");
        usuario.setCelular("923585964");
        usuario.setDireccion("Av. Ciruelos 100");
        usuario.setDni("72924244");
        usuario.setUsername("marisolpr");
        usuario.setFechaNacimiento(new Date());
        usuario.setRol("ADMIN");
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        usuarioActual = new Usuario();
        usuarioActual.setId(usuarioId);
        usuarioActual.setNombres("Marisol");
        usuarioActual.setApellidos("Pachauri Rivera");
        usuarioActual.setEmail("marisol@gmail.com");
        usuarioActual.setUsername("marisolpr");

        reniecResponse = new ReniecResponse();
        reniecResponse.setFirstName("Marisol");
        reniecResponse.setFirstLastName("Pachauri");
        reniecResponse.setSecondLastName("Rivera");
        reniecResponse.setFullName("Marisol Pachauri Rivera");
        reniecResponse.setDocumentNumber("72924244");
    }

    @Test
    void createIn_whenDataIsValid_shouldCreateUsuario() {
        when(reniecPortOut.getPersonaInfo(usuario.getDni())).thenReturn(reniecResponse);
        when(usuarioPortOut.existsByUsername(usuario.getUsername())).thenReturn(false);
        when(usuarioPortOut.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioPortOut.existsRole(usuario.getRol())).thenReturn(true);
        when(usuarioPortOut.createOut(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.createIn(usuario);

        assertSame(usuario, resultado);
        assertEquals("Marisol", usuario.getNombres());
        assertEquals("Pachauri Rivera", usuario.getApellidos());
        verify(usuarioPortOut).createOut(usuario);
    }

    @Test
    void createIn_whenUsernameExists_shouldThrowDuplicateResourceException() {
        when(reniecPortOut.getPersonaInfo(usuario.getDni())).thenReturn(reniecResponse);
        when(usuarioPortOut.existsByUsername(usuario.getUsername())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.createIn(usuario));

        verify(usuarioPortOut, never()).createOut(usuario);
    }

    @Test
    void createIn_whenEmailExists_shouldThrowDuplicateResourceException() {
        when(reniecPortOut.getPersonaInfo(usuario.getDni())).thenReturn(reniecResponse);
        when(usuarioPortOut.existsByUsername(usuario.getUsername())).thenReturn(false);
        when(usuarioPortOut.existsByEmail(usuario.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.createIn(usuario));

        verify(usuarioPortOut, never()).createOut(usuario);
    }

    @Test
    void createIn_whenRolDoesNotExist_shouldThrowResourceNotFoundException() {
        when(reniecPortOut.getPersonaInfo(usuario.getDni())).thenReturn(reniecResponse);
        when(usuarioPortOut.existsByUsername(usuario.getUsername())).thenReturn(false);
        when(usuarioPortOut.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioPortOut.existsRole(usuario.getRol())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.createIn(usuario));

        verify(usuarioPortOut, never()).createOut(usuario);
    }

    @Test
    void findByIdIn_shouldDelegateToPort() {
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioActual);

        Usuario resultado = usuarioService.findByIdIn(usuarioId);

        assertSame(usuarioActual, resultado);
        verify(usuarioPortOut).findByIdOut(usuarioId);
    }

    @Test
    void findAllIn_shouldDelegateToPort() {
        when(usuarioPortOut.findAllOut()).thenReturn(List.of(usuarioActual));

        List<Usuario> resultado = usuarioService.findAllIn();

        assertEquals(1, resultado.size());
        assertSame(usuarioActual, resultado.get(0));
        verify(usuarioPortOut).findAllOut();
    }

    @Test
    void updateIn_whenNoConflicts_shouldUpdateUsuario() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setEmail("nuevo@gmail.com");
        usuarioActualizado.setUsername("nuevo_user");

        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioActual);
        when(usuarioPortOut.existsByEmail(usuarioActualizado.getEmail())).thenReturn(false);
        when(usuarioPortOut.existsByUsername(usuarioActualizado.getUsername())).thenReturn(false);
        when(usuarioPortOut.updateOut(usuarioId, usuarioActualizado)).thenReturn(usuarioActualizado);

        Usuario resultado = usuarioService.updateIn(usuarioId, usuarioActualizado);

        assertSame(usuarioActualizado, resultado);
        verify(usuarioPortOut).updateOut(usuarioId, usuarioActualizado);
    }

    @Test
    void updateIn_whenEmailConflicts_shouldThrowDuplicateResourceException() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setEmail("otro@gmail.com");
        usuarioActualizado.setUsername("marisolpr");

        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioActual);
        when(usuarioPortOut.existsByEmail(usuarioActualizado.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.updateIn(usuarioId, usuarioActualizado));

        verify(usuarioPortOut, never()).updateOut(usuarioId, usuarioActualizado);
    }

    @Test
    void updateIn_whenUsernameConflicts_shouldThrowDuplicateResourceException() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setEmail("marisol@gmail.com");
        usuarioActualizado.setUsername("nuevo_user");

        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioActual);
        when(usuarioPortOut.existsByEmail(usuarioActualizado.getEmail())).thenReturn(false);
        when(usuarioPortOut.existsByUsername(usuarioActualizado.getUsername())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.updateIn(usuarioId, usuarioActualizado));

        verify(usuarioPortOut, never()).updateOut(usuarioId, usuarioActualizado);
    }

    @Test
    void updateIn_whenSameEmailAndUsernameShouldAllowUpdate() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setEmail("marisol@gmail.com");
        usuarioActualizado.setUsername("marisolpr");

        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioActual);
        when(usuarioPortOut.existsByEmail(usuarioActualizado.getEmail())).thenReturn(true);
        when(usuarioPortOut.existsByUsername(usuarioActualizado.getUsername())).thenReturn(true);
        when(usuarioPortOut.updateOut(usuarioId, usuarioActualizado)).thenReturn(usuarioActualizado);

        Usuario resultado = usuarioService.updateIn(usuarioId, usuarioActualizado);

        assertSame(usuarioActualizado, resultado);
        verify(usuarioPortOut).updateOut(usuarioId, usuarioActualizado);
    }

    @Test
    void deleteIn_whenUsuarioExists_shouldDelete() {
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(usuarioActual);

        usuarioService.deleteIn(usuarioId);

        verify(usuarioPortOut).deleteOut(usuarioId);
    }

    @Test
    void deleteIn_whenUsuarioDoesNotExist_shouldThrowResourceNotFoundException() {
        when(usuarioPortOut.findByIdOut(usuarioId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.deleteIn(usuarioId));

        verify(usuarioPortOut, never()).deleteOut(usuarioId);
    }

    @Test
    void findByUsername_shouldDelegateToPort() {
        when(usuarioPortOut.findByUsername("marisolpr")).thenReturn(usuarioActual);

        Usuario resultado = usuarioService.findByUsername("marisolpr");

        assertSame(usuarioActual, resultado);
        verify(usuarioPortOut).findByUsername("marisolpr");
    }

    @Test
    void createIn_shouldNotCallCreateWhenReniecIsQueriedAndUsernameAlreadyExists() {
        when(reniecPortOut.getPersonaInfo(usuario.getDni())).thenReturn(reniecResponse);
        when(usuarioPortOut.existsByUsername(usuario.getUsername())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.createIn(usuario));

        verify(usuarioPortOut, never()).createOut(usuario);
        verify(usuarioPortOut, never()).existsByEmail(usuario.getEmail());
        verify(usuarioPortOut, never()).existsRole(usuario.getRol());
    }
}
