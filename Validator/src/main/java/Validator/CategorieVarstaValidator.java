package Validator;
import Exceptions.ValidationException;

/**
 * Created by Utilizator on 20-Mar-17.
 */
public class CategorieVarstaValidator {

    public void validate(Integer categorie_id, Integer proba_id, Integer minim_varsta, Integer maxim_varsta) throws ValidationException {
        String errorMessage="";

        if(categorie_id <0)
            errorMessage+="ID de categorie invalid!\n";
        if(proba_id <0)
            errorMessage+="ID de proba invalid\n";
        if(minim_varsta > maxim_varsta)
            errorMessage+="Limite de varsta invalide\n";

        if(errorMessage!="")
            throw new ValidationException(errorMessage);
    }
}
