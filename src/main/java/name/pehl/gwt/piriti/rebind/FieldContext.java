package name.pehl.gwt.piriti.rebind;

import java.util.Date;

import name.pehl.gwt.piriti.client.xml.XmlField;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JEnumType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class FieldContext
{
    private static final String VALUE_VARIABLE = "value";
    private static final String AS_STRING_SUFFIX = "AsString";
    private static final String READER_SUFFIX = "Reader";

    private final TypeOracle typeOracle;
    private final String sourceType;
    private final String sourceVariable;
    private final XmlField xmlField;
    private final JField field;
    private final JType type;
    private final String xpath;
    private final String format;
    private final String valueVariable;
    private final String valueAsStringVariable;
    private final String valueReaderVariable;


    public FieldContext(GeneratorContext context, String sourceType, String sourceVariable, XmlField xmlField,
            JField field, int counter) throws UnableToCompleteException
    {
        // constructor parameters
        this.typeOracle = context.getTypeOracle();
        this.sourceType = sourceType;
        this.sourceVariable = sourceVariable;
        this.xmlField = xmlField;
        this.field = field;

        // calculated values
        JPrimitiveType primitiveType = field.getType().isPrimitive();
        if (primitiveType != null) // isPrimitive() is not available here!
        {
            try
            {
                // Use the boxed type for primitives
                this.type = typeOracle.getType(primitiveType.getQualifiedBoxedSourceName());
            }
            catch (NotFoundException e)
            {
                throw new UnableToCompleteException();
            }
        }
        else
        {
            this.type = field.getType();
        }
        this.xpath = xpathFromType();
        this.format = xmlField.format().equals("") ? null : xmlField.format();
        this.valueVariable = VALUE_VARIABLE + counter;
        this.valueAsStringVariable = valueVariable + AS_STRING_SUFFIX;
        this.valueReaderVariable = valueVariable + READER_SUFFIX;
    }


    private String xpathFromType()
    {
        String xpath = xmlField.value();
        if ("".equals(xpath))
        {
            xpath = field.getName();
            if (isPrimitive() || isBasicType() || isEnum())
            {
                xpath += "/text()";
            }
        }
        return xpath;
    }


    public boolean isPrimitive()
    {
        return getPrimitiveType() != null;
    }


    public JPrimitiveType getPrimitiveType()
    {
        return type.isPrimitive();
    }


    public boolean isBasicType()
    {
        if (Boolean.class.getName().equals(type.getQualifiedSourceName())
                || Byte.class.getName().equals(type.getQualifiedSourceName())
                || Character.class.getName().equals(type.getQualifiedSourceName())
                || Date.class.getName().equals(type.getQualifiedSourceName())
                || Double.class.getName().equals(type.getQualifiedSourceName())
                || Float.class.getName().equals(type.getQualifiedSourceName())
                || Integer.class.getName().equals(type.getQualifiedSourceName())
                || Long.class.getName().equals(type.getQualifiedSourceName())
                || Short.class.getName().equals(type.getQualifiedSourceName())
                || String.class.getName().equals(type.getQualifiedSourceName()))
        {
            return true;
        }
        return false;
    }


    public boolean isEnum()
    {
        return getEnumType() != null;
    }


    public JEnumType getEnumType()
    {
        return type.isEnum();
    }


    public boolean isClassOrInterface()
    {
        return getClassOrInterfaceType() != null;
    }


    public JClassType getClassOrInterfaceType()
    {
        return type.isClass();
    }


    public boolean isArray()
    {
        return getArrayType() != null;
    }


    public JArrayType getArrayType()
    {
        return type.isArray();
    }


    public boolean isCollection()
    {
        return false;
    }


    public boolean isMap()
    {
        return false;
    }


    public TypeOracle getTypeOracle()
    {
        return typeOracle;
    }


    public String getSourceType()
    {
        return sourceType;
    }


    public String getSourceVariable()
    {
        return sourceVariable;
    }


    public XmlField getXmlField()
    {
        return xmlField;
    }


    public JField getField()
    {
        return field;
    }


    public JType getType()
    {
        return type;
    }


    public String getXpath()
    {
        return xpath;
    }


    public String getFormat()
    {
        return format;
    }


    public String getValueVariable()
    {
        return valueVariable;
    }


    public String getValueAsStringVariable()
    {
        return valueAsStringVariable;
    }


    public String getValueReaderVariable()
    {
        return valueReaderVariable;
    }
}