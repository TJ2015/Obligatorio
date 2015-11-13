package util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.primefaces.model.UploadedFile;

public final class Imagenes {
	
	public static final String obtenerNombreImagen(UploadedFile file)
	{
		return (file != null ? file.getFileName() : null);
	}
	
	@SuppressWarnings("resource")
	public final static byte[] convertirInputStreamToArrayByte(UploadedFile file)
	{
		byte[] bytes = null;
		try {
			if (file != null) {
				bytes = new byte[(int)file.getSize()];
				InputStream in = file.getInputstream();
				OutputStream out = new FileOutputStream(new File(file.getFileName()));
				out.write(bytes, 0, in.read(bytes));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	@SuppressWarnings("resource")
	public final static byte[] convertirUrlToArrayByte(String urlString)
	{
		byte[] bytes = null;
		try {
			URL url = new URL(urlString);
			InputStream is = url.openStream();
			bytes = new byte[1048576*8];
			//InputStream in = file.getInputstream();
			OutputStream out = new FileOutputStream(new File("imagen.jpg"));
			out.write(bytes, 0, is.read(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public final static InputStream convertirArrayByteToInputStream(byte[] bytes)
	{
		InputStream inputStream = null;
		try {
			if (bytes != null) {
				inputStream = new ByteArrayInputStream(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	} 
}
