package net.eleritec.utils;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.eleritec.utils.io.IOUtil;

public class ResourceUtil {
	private static final Logger logger = Logger.getLogger(ResourceUtil.class.getName());
	
	/**
	 * Resolves a URL pointing to a resource at the specified pathname.  At present the
	 * pathname may be either a filesystem resource or a classpath resource.  In the 
	 * event of a collision (the resource exists in both the classpath and filesystem), 
	 * a URL to the classpath resource is returned.  If a resource could not be found 
	 * in any of the allowed locations then this method returns null. 
	 * @param pathname the absolute pathname of the resource to find.
	 * @return a URL referencing a resource at the specified pathname; null if no 
	 * resource can be found.
	 */
	public static URL findResource(String pathname) {
		URL url = getClasspathResource(pathname);
		if(url==null) {
			url = getFileResource(pathname);
		}
		return url;
	}
	
	/**
	 * Resovles a URL pointing to a file on the host machine.  If the supplied 
	 * pathname is null, the file doesn't exist, the file isn't a regular file (e.g. it 
	 * is a directory), or the current process does not have permissions to read the file, 
	 * then this method returns null.
	 * @param pathname the pathname used to look for a file.
	 * @return a URL pointing to a file on the host machine; null if a valid file
	 * could not be found.
	 */
	public static URL getFileResource(String pathname) {
		File file = pathname==null? null: new File(pathname);
		try {
			return file!=null && file.exists() && file.isFile()? file.toURI().toURL(): null;
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * Resolves a URL for the specified pathname pointing to a resource in the
	 * classpath.  This method only performs absolute resource lookups, expecting that
	 * the supplied pathname begins with "/".  If it doesn't, then this method prepends
	 * the "/" prefix before performing the lookup.  At present, this method only uses
	 * ResourceUtil's classloader and the system classloader for lookups.  This may 
	 * change in the future.
	 * @param pathname the absolute pathname in the classpath of the resource to find.
	 * @returna URL for the specified pathname pointing to a resource in the
	 * classpath; null if no resource could be found.
	 */
	public static URL getClasspathResource(String pathname) {
		// TODO: make this method a little more versatile.  right now it's only going to 
		// use ResourceUtils's classloader and the system classloader. we should actually 
		// implement some classloader tree walking later on if time permits.
		URL url = null;
		if(pathname!=null) {
			// we only want to do absolute pathname lookups.  nobody cares 
			// about looking up resources in this classes' package.
			pathname = pathname.startsWith("/")? pathname: "/" + pathname;
			url = ResourceUtil.class.getResource(pathname);
			if(url==null) {
				url = ClassLoader.getSystemResource(pathname);
			}
		}
		return url;
	}
	
	public static String readText(String uri) {
		return readText(findResource(uri));
	}
	
	public static String readText(URL url) {
		InputStream in = null;
		try {
			in = url==null? null: url.openStream();
			return IOUtil.readText(in);
		}
		catch(Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
		finally {
			IOUtil.close(in);
		}
	}
	
	public static byte[] readBytes(String uri) {
		return readBytes(findResource(uri));
	}
	
	public static byte[] readBytes(URL url) {
		if(url==null) {
			return null;
		}
		
		InputStream in = null;
		try {
			in = url.openStream();
			return IOUtil.readBytes(in);
		}
		catch(Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
		finally {
			IOUtil.close(in);
		}
	}
	


}