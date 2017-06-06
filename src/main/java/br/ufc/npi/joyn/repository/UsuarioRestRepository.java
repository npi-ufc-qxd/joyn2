package br.ufc.npi.joyn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.ufc.npi.joyn.model.Usuario;

@PreAuthorize("hasRole('USUARIO')")
@RepositoryRestResource(collectionResourceRel = "usuario", path = "usuario")
public interface UsuarioRestRepository extends JpaRepository<Usuario, Long> {

}
