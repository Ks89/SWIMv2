package it.swim.util;

import it.swim.util.exceptions.FotoException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileItem;

/**
 * Classe per convertire una foto passata come fileItem in blob, ridimensionandola.
 */
@Slf4j
public class ConvertitoreFotoInBlob {

	/**
	 * Metodo per convertire un fileItem in blob, per poterlo salvare sul database
	 * @param item : FileItem ottenuta nella servlet
	 * @param lunghezza : int che rappresenta la lunghezza che dovra' avere l'immagine sul database
	 * @param altezza : int che rappresenta l'altezza che dovra' avere l'immagine sul database
	 * @return <b>blob</b> che rappresenta l'oggetto gia' ridimensionato, pronto per essere salvato sul database
	 * @throws IOException errore durante il ridimensionamento oppure nella conversione in Blob
	 * @throws SerialException errore nella conversione in Blob
	 * @throws SQLException errore nella conversione in Blob
	 * @throws FotoException errore dovuto all'upload di un file che non e' una foto, oppure lo e' ma il sistema non la riconosce come tale
	 */
	public static Blob getBlobFromFileItem(FileItem item, int lunghezza, int altezza) throws IOException, SerialException, SQLException, FotoException {
		Blob blob = null;
		InputStream filecontent = item.getInputStream();
		byte[] b = new byte[filecontent.available()];
		log.debug("inputstream blob: " + filecontent.available());
		if(filecontent.available()>0) {
			filecontent.read(b); //metto i dati nell'array b che uso nei metodi successivi

			//per poter ridimensionare l'immagine ho bisogno dia vere un BufferedImage, allora converto il byte[] in BufferedImage
			BufferedImage bufferedImage = ImageResizer.convertiByteArrayInBufferedImage(b);

			//attenzione se non e' una immagine o ci sono altri problemi nel capire che il file e' una immagine, la variabile bufferedImage==null
			if(bufferedImage==null) {
				throw new FotoException(FotoException.Causa.NONRICONOSCIUTACOMEFOTO);
			} else {
				//ridimensiono l'immagine e riottengo un riferimento BufferedImage
				BufferedImage ridimensionata = ImageResizer.ridimensionaImmagine(bufferedImage, lunghezza, altezza);

				//ora riconverto la bufferedImage in byte[]
				byte[] ridottaConvertita = ImageResizer.convertiBufferedImageInByteArray(ridimensionata);

				//infine converto l'immagine in byte[] in un Blob per salvarlo sul database
				blob = new SerialBlob(ridottaConvertita);
			}
		}
		filecontent.close();
		return blob;
	}
}
