
package modelo.AutenticacionDelegate;

import configuracion.ActiveDirectory;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import modelo.UsuariosManagers.Usuarios;

    
public class Ldap {

    private String usuario = "";
    private String dnPrincipal = "";
    
    /** Creates a new instance of Ldap */
    public Ldap() {
    }

    
     //Se utiliza para Buscar los Grupo(s) al(os) que pertenece el usuario
        /*String[] ATTRIBUTES = {"sn", "givenName", "mail", "memberOf"};
        String ATTRIBUTE_FOR_USER = "sAMAccountName";*/
    public static boolean autenticar(String user, String pass) {
        DirContext ctx = null;
        Hashtable env = new Hashtable(5, 0.75f);
        
        //Se utiliza para la parte de autenticacion
        String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
        //Informacion de la Maquina y el pueto del directorio activo        
        String MY_HOST = ActiveDirectory.getString("MY_HOST");
        String TYPE_AUTHENTICATION = ActiveDirectory.getString("TYPE_AUTHENTICATION");
        //Es el dominio sobre el que se encuentra el directorio
        String DOMAIN = ActiveDirectory.getString("DOMAIN");       
        //Se especifica el alcance de la busqueda        
        String SEARCH_BASE = ActiveDirectory.getString("SEARCH_BASE");

        if (pass.compareTo("") == 0 || user.compareTo("") == 0) {
            return false;
        }
        
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
        env.put(Context.PROVIDER_URL, MY_HOST);
        env.put(Context.SECURITY_AUTHENTICATION, TYPE_AUTHENTICATION);        
        env.put(Context.SECURITY_PRINCIPAL,  user+'@'+DOMAIN);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        try {
           
                ctx = new InitialDirContext(env);
                
                
              /* SearchControls constraints = new SearchControls();
                constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String filter = "(&(objectCategory=Person)(sAMAccountName=" + user + "))";
                
                NamingEnumeration<SearchResult> results = ctx.search(SEARCH_BASE, filter, constraints);

                SearchResult sr = null;
                Attributes att = null;
                if (results.hasMore()) {
                    
                    Properties env1 = new Properties();
                    env1.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
                    env1.put(Context.PROVIDER_URL, MY_HOST);
                    
                    SearchResult result = (SearchResult) results.next();
                    
                    Attributes attrs = result.getAttributes();
                    Attribute dnAttr = attrs.get("distinguishedName");
                    String dn = (String) dnAttr.get();
                    
                    env1.put(Context.SECURITY_PRINCIPAL, dn);
                    env1.put(Context.SECURITY_CREDENTIALS, pass); 
                    
                    sr = (SearchResult) results.next();
                    att = (Attributes) sr.getAttributes();
                    NamingEnumeration ne = null;     
                    
                    new InitialDirContext(env1);
                    
                    ctx.close();
                                       
                }else{
                    return false;
                }*/
           
            
        } catch (Exception e) {
            return false;
            
        } finally {
            try {
                ctx.close();
            } catch (Exception ex) {
            }
        }
        return true;
    }
}