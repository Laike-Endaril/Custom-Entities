package com.fantasticsource.customentities.ecs.component.inheritable;

import com.fantasticsource.customentities.ecs.component.base.CStringUTF8;
import com.fantasticsource.customentities.ecs.entity.Entity;
import com.fantasticsource.tools.Tools;

public class CIStringUTF8 extends InheritableComponent<CStringUTF8>
{
    private static final String validChars = "abcdefghijklmnopqrstuvwxyz0123456789_ ";

    public CIStringUTF8(Entity parent, Class<CStringUTF8> componentClass)
    {
        super(parent, componentClass);
    }

    @Override
    public void updateValidity()
    {
        boolean notEmpty = false;
        for (String token : Tools.fixedSplit(value, "\\+"))
        {
            token = token.trim();

            if (token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"')
            {
                token = token.substring(1, token.length() - 2);
            }

            if (token.equals(""))
            {
                valid = false;
                return;
            }

            for (char c : token.toLowerCase().toCharArray())
            {
                if (!validChars.contains("" + c))
                {
                    valid = false;
                    return;
                }
                else if (c != ' ') notEmpty = true;
            }
        }

        valid = notEmpty;
    }

    @Override
    public CStringUTF8 getCalculatedComponent()
    {
        StringBuilder result = new StringBuilder();
        for (String token : Tools.fixedSplit(value, "\\+"))
        {
            token = token.trim();

            if (token.equals("p"))
            {
                token = getParentCalculatedComponent().value;
            }
            else if (token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"')
            {
                token = token.substring(1, token.length() - 2);
            }

            if (token.equals(""))
            {
                valid = false;
                return null;
            }

            for (char c : token.toLowerCase().toCharArray())
            {
                if (!validChars.contains("" + c))
                {
                    valid = false;
                    return null;
                }
            }

            result.append(token);
        }

        valid = true;
        return new CStringUTF8().set(result.toString().trim());
    }
}
