package openwis.pilot.rdsh.server.manager.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import openwis.pilot.rdsh.server.common.dto.SettingDTO;
import openwis.pilot.rdsh.server.manager.mappers.SettingMapperImpl;
import openwis.pilot.rdsh.server.manager.model.QSetting;
import openwis.pilot.rdsh.server.manager.model.Setting;
import openwis.pilot.rdsh.server.manager.service.SettingsService;
import org.apache.commons.lang3.ArrayUtils;
import org.ops4j.pax.cdi.api.OsgiService;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import java.io.IOException;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Transactional
@OsgiServiceProvider(classes = {SettingsService.class})
public class SettingsServiceImpl implements SettingsService {
  // Entity Manager ref.
  @PersistenceContext(unitName = "rdsh-pu")
  private EntityManager em;

  // Logger reference.
  private static final Logger LOGGER = Logger.getLogger(SettingsServiceImpl.class.getName());

  // Reference to OSGi Config Admin service.
  @OsgiService
  @Inject
  private ConfigurationAdmin configAdmin;

  // QueryDSL helpers.
  QSetting qSetting = QSetting.setting;

  // List of settings which should be maintained in Config Admin.
  private static final String[] CONFIG_ADMIN_SETTINGS = new String[]{
      "mqtt_host", "mqtt_username", "mqtt_password", "jwt_secret"
  };

  // The PID under which configuration is kept in Config Admin for this bundle.
  private static final String CONFIG_ADMIN_PID = "openwis.rdsh";

  @Override
  public List<SettingDTO> getSettings() {
    // Fetch settings from database.
    final List<Setting> settings = new JPAQueryFactory(em)
        .selectFrom(qSetting)
        .orderBy(qSetting.settingKey.asc())
        .fetch();
    List<SettingDTO> retVal =  new SettingMapperImpl().toSettingDTOList(settings);

    // Fetch settings from Config Admin.
    try {
      Configuration conf = configAdmin.getConfiguration(CONFIG_ADMIN_PID);
      @SuppressWarnings("unchecked")
      Dictionary<String, Object> props = conf.getProperties();
      for (String key : CONFIG_ADMIN_SETTINGS) {
        retVal.add(new SettingDTO(key, (String)props.get(key)));
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Could not parse PID.", e);
    }catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error @ getSettings.", e);
        e.printStackTrace();
      }

    return retVal;
  }

  @Override
  public void saveSettings(List<SettingDTO> settings) {
    for (SettingDTO setting : settings) {
      // Update in config
      if (ArrayUtils.contains(CONFIG_ADMIN_SETTINGS, setting.getSettingKey())) { // Update in Config Admin.
        try {
          Configuration conf = configAdmin.getConfiguration(CONFIG_ADMIN_PID);
          @SuppressWarnings("unchecked")
          Dictionary<String, Object> props = conf.getProperties();
          props.put(setting.getSettingKey(), setting.getSettingVal());
          conf.update(props);
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, "Could not parse PID.", e);
        }
      } else { // Update in database.
        new JPAQueryFactory(em)
            .update(qSetting)
            .where(qSetting.settingKey.eq(setting.getSettingKey()))
            .set(qSetting.settingVal, setting.getSettingVal())
            .execute();
      }
    }
  }

  @Override
  public Optional<SettingDTO> getSetting(String settingKey) {
    return getSettings().stream().filter(o -> o.getSettingKey().equals(settingKey)).findFirst();
  }
}
