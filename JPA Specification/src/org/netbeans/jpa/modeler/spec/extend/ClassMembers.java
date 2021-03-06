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
package org.netbeans.jpa.modeler.spec.extend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

/**
 *
 * @author Gaurav Gupta
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassMembers {

    @XmlIDREF
    @XmlElement(name = "a")
    protected List<Attribute> attributes;

    public boolean addAttribute(Attribute attribute) {
        return getAttributes().add(attribute);
    }

    public boolean isExist(Attribute attribute) {
        return getAttributes().stream().filter(a -> a == attribute).findAny().isPresent();
    }

    public boolean removeAttribute(Attribute attribute) {
        return getAttributes().remove(attribute);
    }

    /**
     * @return the attributes
     */
    public List<Attribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        return attributes;
    }

    public List<String> getAttributeNames() {
        return getAttributes().stream().map((Attribute a) -> a.getName()).collect(toList());
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return getAttributes().stream().map((Attribute a) -> a.getDataTypeLabel() + " " + a.getName()).collect(Collectors.joining(", "));
    }

}
