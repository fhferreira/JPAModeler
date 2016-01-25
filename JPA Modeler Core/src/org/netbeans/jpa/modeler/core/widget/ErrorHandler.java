/**
 * Copyright [2014] Gaurav Gupta
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
package org.netbeans.jpa.modeler.core.widget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import org.netbeans.jpa.modeler.rules.attribute.AttributeValidator;
import org.netbeans.jpa.modeler.rules.entity.EntityValidator;
import org.netbeans.modeler.widget.node.IWidget;
import org.netbeans.modeler.widget.node.WidgetStateHandler;
import org.openide.util.NbBundle;

/**
 *
 * @author Gaurav Gupta
 */
public class ErrorHandler {

    private final IWidget widget;
    

    public ErrorHandler(IWidget widget) {
        this.widget = widget;
    }

    private final java.util.Map<String, String> errorList = new HashMap<>();

    public void throwError(String key) {
        errorList.put(key, ResourceBundleManager.get(key));//NbBundle.getMessage(bundle.getClass(), key));
        printError();
    }

    public void clearError(String key) {
        errorList.remove(key);
        printError();
    }

    public void printError() {
        StringBuilder errorMessage = new StringBuilder();
        errorList.keySet().stream().forEach((errorKey) -> {
            errorMessage.append(errorList.get(errorKey));
        });
        if (errorMessage.length() != 0) {
            widget.setToolTipText(errorMessage.toString());
            if (widget instanceof WidgetStateHandler) {
                ((WidgetStateHandler) widget).setErrorState(true);
            }
        } else {
            widget.setToolTipText(null);
            if (widget instanceof WidgetStateHandler) {
                ((WidgetStateHandler) widget).setErrorState(false);
            }
        }
    }

    
    private static class ResourceBundleManager {

        private static final Map<String, String> ERRORS = new HashMap<>();
        private static final Class[] VALIDATORS = {EntityValidator.class, AttributeValidator.class};

        private static String get(String key) {
            String value = ERRORS.get(key);
            if (value != null) {
                return value;
            }
            for (Class validator : VALIDATORS) {
                try {
                    value = NbBundle.getMessage(validator, key);
                    ERRORS.put(key, value);
                    return value;
                } catch (MissingResourceException resourceException) {
                    //Ignore
                }
            }
            throw new MissingResourceException(key + " not found", Arrays.toString(VALIDATORS), key);
        }

    }
}
