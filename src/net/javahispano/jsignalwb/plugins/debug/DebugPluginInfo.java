/*
 * DebugPluginInfo.java
 *
 * Created on 24 de julio de 2007, 1:35
 */

package net.javahispano.jsignalwb.plugins.debug;

import net.javahispano.jsignalwb.plugins.Plugin;
import net.javahispano.jsignalwb.plugins.Plugin.PluginTypes;

/**
 *
 * @author Roman Segador
 */
public class DebugPluginInfo {
    private PluginTypes pluginType;
    private String pluginName;
    private Plugin plugin;

    public DebugPluginInfo(PluginTypes pluginType, String pluginName, Plugin plugin) {
        this.plugin = plugin;
        this.pluginName = pluginName;
        this.pluginType = pluginType;
    }

    public DebugPluginInfo(String pluginType, String pluginName, Plugin plugin) {
        this.plugin = plugin;
        this.pluginName = pluginName;
        this.pluginType = getPluginType(pluginType);
    }

    public String getPluginType() {
        if (pluginType == PluginTypes.ALGORITHM) {
            return "algorithm";
        } else if (pluginType == PluginTypes.ANNOTATION) {
            return "anotation";
        } else if (pluginType == PluginTypes.GENERIC) {
            return "generic";
        } else if (pluginType == PluginTypes.GRID) {
            return "grid";
        } else if (pluginType == PluginTypes.MARK) {
            return "mark";
        } else if (pluginType == PluginTypes.LOADER) {
            return "loader";
        } else {
            return "saver";
        }

    }

    private static PluginTypes getPluginType(String p) {
        if ("algorithm".equals(p)) {
            return PluginTypes.ALGORITHM;
        }
        if ("anotation".equals(p)) {
            return PluginTypes.ANNOTATION;
        }
        if ("generic".equals(p)) {
            return PluginTypes.GENERIC;
        }
        if ("grid".equals(p)) {
            return PluginTypes.GRID;
        }
        if ("mark".equals(p)) {
            return PluginTypes.MARK;
        }
        if ("loader".equals(p)) {
            return PluginTypes.LOADER;
        }
        return PluginTypes.SAVER;
    }

    public String getPluginName() {
        return pluginName;
    }

    public Plugin getPlugin() {
        return plugin;
    }

}
