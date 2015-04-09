//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.jpa.source.JavaSourceParserUtil;

/**
 *
 *
 * @Target({}) @Retention(RUNTIME) public @interface UniqueConstraint { String
 * name() default ""; String[] columnNames(); }
 *
 *
 *
 * <p>
 * Java class for unique-constraint complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="unique-constraint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="column-name" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unique-constraint", propOrder = {
    "columnName"
})
public class UniqueConstraint {

    @XmlElement(name = "column-name", required = true)
    protected List<String> columnName;
    @XmlAttribute
    protected String name;

    public static UniqueConstraint load(Element element, AnnotationMirror annotationMirror) {
        if (annotationMirror == null) {
            annotationMirror = JavaSourceParserUtil.findAnnotation(element, "javax.persistence.UniqueConstraint");
        }
        UniqueConstraint uniqueConstraint = null;
        if (annotationMirror != null) {
            uniqueConstraint = new UniqueConstraint();
            List columnNameList = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "columnNames");
            if (columnNameList != null) {
                for (Object object : columnNameList) {
                    uniqueConstraint.getColumnName().add(object.toString());
                }
            }
            uniqueConstraint.name = (String) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "name");
        }
        return uniqueConstraint;
    }

    /**
     * Gets the value of the columnName property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the columnName property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColumnName().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     */
    public List<String> getColumnName() {
        if (columnName == null) {
            columnName = new ArrayList<String>();
        }
        return this.columnName;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

}
