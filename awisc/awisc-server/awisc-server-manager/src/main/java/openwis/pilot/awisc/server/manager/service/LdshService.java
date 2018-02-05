package openwis.pilot.awisc.server.manager.service;

import java.util.List;

import openwis.pilot.awisc.server.common.dto.LdshDTO;

public interface LdshService {

  /**
   * Obtain the list of all registered Ldshs.
   * @return Returns the list of all Ldshs registered in the system.
   */
  List<LdshDTO> getLdsh();

  /**
   * Finds a specific Ldsh by Id.
   * @param ldshId The Id of the Ldsh to fetch.
   * @return Returns the requested LDSDH or null.
   */
  LdshDTO getLdsh(Long ldshId);

  /**
   * Deletes a previously registered Ldsh.
   * @param id The Id of the Ldsh to delete.
   */
  void deleteLdsh(Long id);

  /**
   * Registers or updates an Ldsh into the system.
   * @param ldshDTO The DTO with the information of the Ldsh to save or update.
   * @return Returns the Ldsh that was saved or created.
   */
  LdshDTO saveLdsh(LdshDTO ldshDTO);

  /**
   * Checks whether an Ldsh token is registered into the system.
   * @param ldshToken The Ldsh token to check.
   * @return Returns true if the token exists, false otherwise.
   */
  boolean validateLdshToken(String ldshToken);
}