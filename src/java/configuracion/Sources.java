package configuracion;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Sources {

    private static final String BUNDLE_NAME = "configuracion.Sources"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
                    .getBundle(BUNDLE_NAME);

    
    
    private Sources() {
    }

    
    
    /**
     * 
     * Busca la clave en el properties y obtiene el valor.
     * 
     * @param key
     * @return 
     */
    public static String getString(String key) {
            try {

                    return RESOURCE_BUNDLE.getString(key);
            } catch (MissingResourceException e) {
                    return '!' + key + '!';
            }
    }
}
