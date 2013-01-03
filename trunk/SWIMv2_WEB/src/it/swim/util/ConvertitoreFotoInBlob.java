//package it.swim.util;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Blob;
//import java.sql.SQLException;
//
//import javax.sql.rowset.serial.SerialBlob;
//import javax.sql.rowset.serial.SerialException;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.apache.commons.fileupload.FileItem;
//
///**
// * Classe per convertire una foto passata come fileItem in blob.
// */
//@Slf4j
//public class ConvertitoreFotoInBlob {
//
//	/**
//	 * Metodo per convertire un fileItem in blob, per poterlo salvare sul database
//	 * @param item : FileItem ottenuta nella servlet
//	 * @param lunghezza : int che rappresenta la lunghezza che dovra' avere l'immagine sul database
//	 * @param altezza : int che rappresenta l'altezza che dovra' avere l'immagine sul database
//	 * @return
//	 * @throws IOException errore durante il ridimensionamento oppure nella conversione in Blob
//	 * @throws SerialException errore nella conversione in Blob
//	 * @throws SQLException errore nella conversione in Blob
//	 */
//	public static Blob getBlobFromFileItem(FileItem item, int lunghezza, int altezza) throws IOException, SerialException, SQLException {
//		Blob blob = null;
//		InputStream filecontent = item.getInputStream();
//		byte[] b = new byte[filecontent.available()];
//		log.debug("inputstream blob: " + filecontent.available());
//		if(filecontent.available()>0) {
//			//ridimensiono l'immagine
//			
//			BufferedImage bufferedImage = ImageResizer.convertiByteArrayInBufferedImage(b);
//			BufferedImage ridimensionata = ImageResizer.ridimensionaImmagine(bufferedImage, lunghezza, altezza);
//			byte[] ridottaConvertita = ImageResizer.convertiBufferedImageInByteArray(ridimensionata);
//
//			filecontent.read(ridottaConvertita);
//			blob = new SerialBlob(ridottaConvertita);
//		}
//		
//		filecontent.close();
//		return blob;
//	}
//}
