package net.eleritec.utils.swing;

import static net.eleritec.utils.object.ClassUtil.findMethod;

import java.awt.Image;
import java.awt.Window;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import javax.swing.RootPaneContainer;

import net.eleritec.utils.collection.MapUtil;
import net.eleritec.utils.object.DuckType;

public interface WindowWrapper extends RootPaneContainer {

    void setUndecorated(boolean undecorated);
    void setResizable(boolean resizable);
    void setDefaultCloseOperation(int operation);
    void pack();
    void setIconImage(Image image);
    
    <W extends RootPaneContainer> W getWindow(Class<W> windowType);
    
    public static WindowWrapper wrap(Window window) {
    	Method method = findMethod(WindowWrapper.class, "getWindow", new Class[] {Class.class});
    	InvocationHandler handler = (p, m, args)->{
    		Class<?> expected = (Class<?>) args[0];
    		return expected.isAssignableFrom(window.getClass())? window: null;
    	};
    	
    	Map<Method, InvocationHandler> defaults = MapUtil.hashMap(method, handler);
    	return DuckType.getDuckType(WindowWrapper.class, window, false, defaults);
    }
}
