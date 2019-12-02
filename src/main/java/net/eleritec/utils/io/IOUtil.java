package net.eleritec.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOUtil {
	private static final Logger logger = Logger.getLogger(IOUtil.class.getName());
	
	public static void close(Closeable closeable) {
		if(closeable!=null) {
			try {
				closeable.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}
	
	public static String readText(InputStream in) {
		String content = null;
		if(in!=null) {
			byte[] buffer = new byte[4096];
			try {
				StringBuilder sb = new StringBuilder();
				for(int read=in.read(buffer); read!=-1; read=in.read(buffer)) {
					byte[] chunk = buffer;
					if(read<buffer.length) {
						chunk = new byte[read];
						System.arraycopy(buffer, 0, chunk, 0, read);
					}
					sb.append(new String(chunk));
				}
				content = sb.toString();
			}
			catch(Exception e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
			finally {
				close(in);
			}
		}
		return content;
	}
	
	public static byte[] readBytes(InputStream in) {
		if(in==null) {
			return null;
		}
		
		byte[] buffer = new byte[4096];
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			for(int read=in.read(buffer); read!=-1; read=in.read(buffer)) {
				out.write(buffer, 0, read);
			}
			return out.toByteArray();
		}
		catch(Exception e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
		finally {
			close(in);
			close(out);
		}
	}
}
