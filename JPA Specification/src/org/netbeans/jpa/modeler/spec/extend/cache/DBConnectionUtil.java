/**
 * Copyright [2016] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.spec.extend.cache;

import javax.swing.JComboBox;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.api.db.explorer.DatabaseException;
import org.netbeans.api.db.explorer.support.DatabaseExplorerUIs;
import org.netbeans.jpa.modeler.collaborate.issues.ExceptionUtils;
import org.netbeans.jpa.modeler.spec.EntityMappings;
import org.netbeans.modeler.core.ModelerFile;

/**
 *
 * @author Shiwani Gupta
 */
public class DBConnectionUtil {

    /**
     * Get connection from combobox
     *
     * @param dbConComboBox
     * @return
     */
    public static DatabaseConnection getConnection(JComboBox dbConComboBox) {
        Object item = dbConComboBox.getSelectedItem();
        if (item instanceof DatabaseConnection) {
            return (DatabaseConnection) item;
        } else {
            return null;
        }
    }

    /**
     * Save connection from combobox
     *
     * @param file
     * @param dbConComboBox
     */
    public static void saveConnection(ModelerFile file, JComboBox dbConComboBox) {
        DatabaseConnection connection = DBConnectionUtil.getConnection(dbConComboBox);
        if (connection != null) {
            Cache cache = ((EntityMappings) file.getDefinitionElement()).getCache();
            DatabaseConnectionCache dbCache = new DatabaseConnectionCache();
            dbCache.setUrl(connection.getDatabaseURL());
            dbCache.setUserName(connection.getUser());
            dbCache.setPassword(connection.getPassword());
            dbCache.setDriverClassName(connection.getDriverClass());
            try {
                dbCache.setDriverClass(connection.getJDBCDriver().getDriver().getClass());
            } catch (DatabaseException ex) {
                file.handleException(ex);
            }
            dbCache.setDatabaseConnection(connection);
            cache.setDatabaseConnection(dbCache);
        }
    }

    public static void loadConnection(ModelerFile file, JComboBox dbConComboBox) {
        loadConnection((EntityMappings) file.getDefinitionElement(), dbConComboBox);
    }

    /**
     * Load combobox with DB connection
     *
     * @param entityMappings
     * @param dbConComboBox
     */
    public static void loadConnection(EntityMappings entityMappings, JComboBox dbConComboBox) {
//            DatabaseConnection connection = DBConnectionUtil.getConnection(dbConComboBox);
        Cache cache = entityMappings.getCache();
        DatabaseConnectionCache dbCache = cache.getDatabaseConnectionCache();

        DatabaseExplorerUIs.connect(dbConComboBox, ConnectionManager.getDefault());
        dbConComboBox.setToolTipText("Available Database Connection");

//        for (int i = 0; i < dbConComboBox.getItemCount(); i++) {
//            Object item = dbConComboBox.getItemAt(i);
//            if (dbCache != null && item instanceof DatabaseConnection && ((DatabaseConnection) item).getDatabaseURL().equals(dbCache.getUrl())) {
//                dbConComboBox.setSelectedIndex(i);
//                try {
//                    dbCache.setDriverClass(((DatabaseConnection) item).getJDBCDriver().getDriver().getClass());
//                    dbCache.setDatabaseConnection((DatabaseConnection) item);
//                } catch (DatabaseException ex) {
//                    ExceptionUtils.printStackTrace(ex);
//                }
//                break;
//            }
//        }
    }

    public static DatabaseConnection getConnection(ModelerFile file) {
        Cache cache = ((EntityMappings) file.getDefinitionElement()).getCache();
        if (cache == null || cache.getDatabaseConnectionCache() == null) {
            return null;//ConnectionManager.getDefault().getConnections()[0];
        }
        return cache.getDatabaseConnectionCache().getDatabaseConnection();
    }

}