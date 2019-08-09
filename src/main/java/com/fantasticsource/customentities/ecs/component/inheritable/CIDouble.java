package com.fantasticsource.customentities.ecs.component.inheritable;

import com.fantasticsource.customentities.ecs.component.base.CDouble;
import com.fantasticsource.customentities.ecs.entity.Entity;
import com.fantasticsource.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String[][] tokenArrays = Tools.preservedSplitSeparated(value, "[+\\-*/]");

        ArrayList<Double> values = new ArrayList<>();
        for (String s : tokenArrays[0])
        {
            s = s.trim();
            if (s.equals(""))
            {
                valid = false;
                return null;
            }
            else if (s.equals("p"))
            {
                values.add(getParentCalculatedComponent().value);
            }
            else
            {
                try
                {
                    double d = Double.parseDouble(s);
                    values.add(d);
                }
                catch (NumberFormatException e)
                {
                    valid = false;
                    return null;
                }
            }
        }


        List<String> operators = Arrays.asList(tokenArrays[1]);

        int index = Tools.min(operators.indexOf("*"), operators.indexOf("/"));
        while (index >= 0)
        {
            if (operators.remove(index).equals("*"))
            {
                values.set(index, values.get(index) * values.remove(index + 1));
            }
            else
            {
                values.set(index, values.get(index) / values.remove(index + 1));
            }
            index = Tools.min(operators.indexOf("*"), operators.indexOf("/"));
        }

        index = Tools.min(operators.indexOf("+"), operators.indexOf("-"));
        while (index >= 0)
        {
            if (operators.remove(index).equals("+"))
            {
                values.set(index, values.get(index) + values.remove(index + 1));
            }
            else
            {
                values.set(index, values.get(index) - values.remove(index + 1));
            }
            index = Tools.min(operators.indexOf("+"), operators.indexOf("-"));
        }


        return new CDouble().set(values.get(0));
    }
}
