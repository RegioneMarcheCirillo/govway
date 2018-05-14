package org.openspcoop2.monitor.engine.dynamic;

import org.openspcoop2.monitor.sdk.condition.Context;
import org.openspcoop2.monitor.sdk.exceptions.ValidationException;
import org.openspcoop2.monitor.sdk.parameters.Parameter;
import org.openspcoop2.monitor.sdk.plugins.ISearchArguments;

import java.util.Iterator;
import java.util.Map;

import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

/**
 * BasicValidator
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class BasicValidator implements IDynamicValidator{
	
	private static Logger log = LoggerWrapperFactory.getLogger(BasicValidator.class);
	
	private String className;
	
	protected BasicValidator(String className) {
		this.className = className;
	}
	
	@Override
	public void validate(Context context) throws ValidationException {
		

		try{
			
			//Class<?> c = Class.forName(this.className);
			IDynamicLoader bl = DynamicFactory.getInstance().newDynamicLoader(this.className, BasicValidator.log);
			
			Object obj = bl.newInstance();
			
			if(obj instanceof ISearchArguments){
				((ISearchArguments) obj).validate(context);
			}else{
				String iface = ISearchArguments.class.getName();
				throw new Exception("La classe ["+this.className+"] non implementa l'interfaccia ["+iface+"]");
			}
			
			// Se non sono stati sollevati errori, verifico che i parametri dichiarati come required possiedano un valore.
			// Il controllo è fatto appositamente dopo il validate sdk, in modo da permettere di personalizzare i controlli dentro il metodo validate
			Map<String, Parameter<?>> mapParams = context.getParameters();
			if(mapParams!=null && mapParams.size()>0){
				Iterator<String> keys = mapParams.keySet().iterator();
				while (keys.hasNext()) {
					String idParameter = (String) keys.next();
					Parameter<?> p = mapParams.get(idParameter);
					if(p.getRendering().isRequired()){
						Object o = p.getValue();
						if(o==null){
							throw new ValidationException("Non è stato fornito un valore per il parametro '"+idParameter+"'");
						}
					}
				}
			}
			
		}catch(ClassNotFoundException cnfe){
			BasicValidator.log.error("Impossibile caricare il plugin. La classe indicata ["+this.className+"] non esiste.",cnfe);
			throw new ValidationException("Impossibile caricare il plugin. La classe indicata ["+this.className+"] non esiste.");
		}catch (ValidationException ve) {
			throw ve;
		}catch (Exception e) {
			BasicValidator.log.error(e.getMessage(),e);
			throw new ValidationException(e.getMessage());
		}
	}
}