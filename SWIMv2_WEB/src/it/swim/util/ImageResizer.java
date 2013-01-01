package it.swim.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

/**
 *	Classe che si occupa del ridimensionamento di immagini.
 */
public final class ImageResizer {
	private static AffineTransform affineTransform;

	/**
	 * Costruttore nascosto perche' inutile (detto da Sonar XD).
	 */
	private ImageResizer() {
	}


	/**
	 * Metodo statico che serve per ottenere un'immagine ridimensionata in base ai parametri forniti in ingresso.
	 * @param originale BufferedImage che rappresenta l'immagine da ridimensionare.
	 * @param lunghezzaRidim int che rappresenta la lunghezza in pixer desiderata.
	 * @param altezzaRidim int che rappresenta l'altezza in pixel desiderata.
	 * @return BufferedImage che rappresenta l'immagine ridimensionata in base ai 3 parametri forniti. In particolare sara' 
	 * l'immagine "originale" con lunghezza "lunghezzaRidim" e altezza "altezzaRidim".
	 */
	public static BufferedImage ridimensionaImmagine(BufferedImage originale, int lunghezzaRidim, int altezzaRidim) {
		BufferedImage ridimensionata = new BufferedImage(lunghezzaRidim, altezzaRidim, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = ridimensionata.createGraphics();
		System.out.println(" dasdfhasfgaskjhfgasjhfgajhsfgjahsgfajsgfajshfgjas  " + originale.getWidth() + " - " + originale.getHeight());
		affineTransform = AffineTransform.getScaleInstance((double)(lunghezzaRidim)/
				originale.getWidth(),(double)(altezzaRidim)/originale.getHeight());
		g2d.drawRenderedImage(originale,affineTransform);
		g2d.dispose();
		return ridimensionata;
	}

	/**
	 * Metodo che converte un bytearray con una immagine in BufferedImage
	 * @param imageInByte = byte[]Êda donvertire
	 * @return <b>BufferedImage</b> che prappresenta l'immagine convertita.
	 * @throws IOException eccezione dovuta ad un errore nella conversione
	 */
	public static BufferedImage convertiByteArrayInBufferedImage(byte[] imageInByte) throws IOException {
		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new ByteArrayInputStream(imageInByte)); 
		BufferedImage image = decoder.decodeAsBufferedImage();
//		InputStream in = new ByteArrayInputStream(imageInByte);
//		BufferedImage bImageFromConvert = ImageIO.read(in);
//		in.close();
		return image;
	}

	/**
	 * Metodo che converte una bufferedimage in bytearray
	 * @param bufferedImage = BufferedImage da convertire
	 * @return <b>byte[]</b> che prappresenta l'immagine convertita.
	 * @throws IOException eccezione dovuta ad un errore nella conversione
	 */
	public static byte[] convertiBufferedImageInByteArray(BufferedImage bufferedImage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
}
