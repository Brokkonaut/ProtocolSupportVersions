package de.iani.psv;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class ProtocolSupportVersionsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        boolean modified = false;
        ConfigurationSection versions = getConfig().getConfigurationSection("versions");
        if (versions == null) {
            versions = getConfig().createSection("versions");
            modified = true;
        }
        for (ProtocolVersion version : ProtocolVersion.values()) {
            if (version.getId() >= 0) {
                if (!versions.contains(version.name())) {
                    versions.set(version.name(), true);
                    modified = true;
                }
                boolean enabled = versions.getBoolean(version.name());
                if (!enabled) {
                    ProtocolSupportAPI.disableProtocolVersion(version);
                }
            }
        }
        if (modified) {
            this.saveConfig();
        }
    }
}
