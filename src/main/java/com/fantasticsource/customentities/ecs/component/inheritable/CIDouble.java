package com.fantasticsource.customentities.ecs.component.inheritable;

import com.fantasticsource.customentities.ecs.component.base.CDouble;
import com.fantasticsource.customentities.ecs.entity.Entity;
import com.fantasticsource.tools.Tools;

public class CIDouble extends InheritableComponent<CDouble>
{
    public CIDouble(Entity parent, Class<CDouble> componentClass)
    {
        super(parent, componentClass);
    }

    @Override
    public void updateValidity()
    {
        boolean notEmpty = false;
        for (String token : Tools.fixedSplit(value, "[+\\-]"))
        {
            for (String token2 : Tools.fixedSplit(token.trim(), "[/*]"))
            {
                token2 = token2.trim();

                if (token2.equals("p")) notEmpty = true;
                else
                {
                    if (token2.equals(""))
                    {
                        valid = false;
                        return;
                    }

                    try
                    {
                        Double.parseDouble(token2);
                    }
                    catch (NumberFormatException e)
                    {
                        valid = false;
                        return;
                    }
                }
            }
        }

        valid = notEmpty;
    }

    @Override
    public CDouble getCalculatedComponent()
    {
        String[] tokens = Tools.preservedSplit(value, "[+\\-*/]");
        String token = tokens[0].trim();
        double result = token == "p" ? getParentCalculatedComponent().value : Double.parseDouble(token);

        boolean shouldBeOperator = true;
        for (int i = 1; i < tokens.length; i++)
        {
            token = tokens[i].trim();
            if (shouldBeOperator)
            {
                if (token == "*")
                {
                    //TODO
                }
                else if (token == "/")
                {
                    //TODO
                }
                else if (token == "+")
                {
                    //TODO
                }
                else if (token == "-")
                {
                    //TODO
                }
                else
                {
                    valid = false;
                    return null;
                }
            }
            else
            {
                if (token == "p")
                {
                    //TODO
                }
                else
                {
                    try
                    {
                        double d = Double.parseDouble(token);
                        //TODO
                    }
                    catch (NumberFormatException e)
                    {
                        valid = false;
                        return null;
                    }
                }
            }

            shouldBeOperator = !shouldBeOperator;
        }

        valid = true;
        return new CDouble().set(result);
    }
}
