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
package org.netbeans.jpa.modeler.db.accessor;

import java.sql.Blob;
import java.sql.Clob;
import static org.eclipse.persistence.internal.jpa.metadata.MetadataConstants.JPA_ENUM_ORDINAL;
import static org.eclipse.persistence.internal.jpa.metadata.MetadataConstants.JPA_ENUM_STRING;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.DirectAccessor;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.ElementCollectionAccessor;
import org.eclipse.persistence.internal.jpa.metadata.converters.EnumeratedMetadata;
import org.eclipse.persistence.internal.jpa.metadata.converters.LobMetadata;
import org.eclipse.persistence.internal.jpa.metadata.converters.TemporalMetadata;
import org.netbeans.jpa.modeler.spec.EnumType;
import org.netbeans.jpa.modeler.spec.Lob;
import org.netbeans.jpa.modeler.spec.TemporalType;

/**
 *
 * @author Gaurav Gupta
 */
public class AccessorUtil {

    public static void setEnumerated(DirectAccessor accessor, EnumType enumType, boolean isCollectionType) {
        if (enumType == null) {
            return;
        }
        EnumeratedMetadata enumeratedMetadata = new EnumeratedMetadata();
        if (enumType == EnumType.STRING) {
            enumeratedMetadata.setEnumeratedType(JPA_ENUM_STRING);
        } else {
            enumeratedMetadata.setEnumeratedType(JPA_ENUM_ORDINAL);
        }
        accessor.setEnumerated(enumeratedMetadata);
        if (isCollectionType) {
            ((ElementCollectionAccessor) accessor).setTargetClassName(ProxyEnum.class.getName());
        } else {
            accessor.setAttributeType(ProxyEnum.class.getName()); //using the proxy enum instead of the orignal enum 
        }
        // orignal enum is not accessible in classloader so either to use proxy enum or create dynamic enum
    }

    public static void setTemporal(DirectAccessor accessor, TemporalType temporalType, boolean isCollectionType) {
        if (temporalType == null) {
            return;
        }
        TemporalMetadata temporalMetadata = new TemporalMetadata();
        temporalMetadata.setTemporalType(temporalType.toString());
        accessor.setTemporal(temporalMetadata);// JPA_TEMPORAL_DATE = "DATE"; JPA_TEMPORAL_TIME = "TIME"; JPA_TEMPORAL_TIMESTAMP = "TIMESTAMP";
    }

    public static void setLob(DirectAccessor accessor, Lob lob, String attributeType, boolean isCollectionType) {
        if (lob == null || attributeType==null) {
            return;
        }
        
        if (attributeType.equals("byte[]") || attributeType.equals("Byte[]")) { //https://github.com/jGauravGupta/jpamodeler/issues/5 , https://github.com/jGauravGupta/jpamodeler/issues/6
            if (isCollectionType) {
                ((ElementCollectionAccessor) accessor).setTargetClassName(Blob.class.getName());
            } else {
                accessor.setAttributeType(Blob.class.getName()); 
            }
        } else if (attributeType.equals("char[]") || attributeType.equals("Character[]")) {
            if (isCollectionType) {
                ((ElementCollectionAccessor) accessor).setTargetClassName(Clob.class.getName());
            } else {
                accessor.setAttributeType(Clob.class.getName()); 
            }
        }
        
        
        
        accessor.setLob(new LobMetadata());

    }

    public static enum ProxyEnum {
        DEFAULT;
    }

}
