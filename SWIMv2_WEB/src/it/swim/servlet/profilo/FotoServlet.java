/*
Copyright 2012-2015 Stefano Cappa, Jacopo Bulla, Davide Caio
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package it.swim.servlet.profilo;

import it.swim.util.ConvertitoreFotoInBlob;
import it.swim.util.exceptions.FotoException;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import exceptions.LoginException;

import sessionBeans.localInterfaces.GestioneCollaborazioniLocal;

/**
 * Servlet per gestire le foto del profilo
 */
@Slf4j
public class FotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneCollaborazioniLocal gestioneCollaborazioni; 

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FotoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("emailUtente");

		log.debug("fotoServlet - emailUtente : " + email);

		Blob blob = null;

		//e' l'admin che richiede la foto, e visto che non puo' averla mostra quella predefinita
		//uso stringa mailAdmin per indentificare se e' l'admin a richiederla
		try {
			if(email!=null && email.equals("mailAdmin")) {
				blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
				if(blob!=null) {
					byte[] foto = blob.getBytes(1, (int)blob.length());
					response.setContentType("image/jpg");
					response.getOutputStream().write(foto);
				}
			}
		} catch(SQLException e) {
			log.error(e.getMessage(), e);
		} catch (FotoException e) {
			log.error(e.getMessage(), e);
		}


		//se e' l'utente o il visitatore
		try {
			if(email==null || email.equals("")) {
				log.debug("fotoServlet - mettoFotoPredefinita_1");
				blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
			} else {
				blob = gestioneCollaborazioni.getUtenteByEmail(email).getFotoProfilo();

				if(blob==null) {
					log.debug("fotoServlet - mettoFotoPredefinita_2");
					blob = ConvertitoreFotoInBlob.getBlobFromDefaultImage();
				}
			}

			if(blob!=null) {
				byte[] foto = blob.getBytes(1, (int)blob.length());
				response.setContentType("image/jpg");
				response.getOutputStream().write(foto);
			}
		} catch(SQLException e) {
			log.error(e.getMessage(), e);
		} catch (LoginException e) {
			log.error(e.getMessage(), e);
		} catch (FotoException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
