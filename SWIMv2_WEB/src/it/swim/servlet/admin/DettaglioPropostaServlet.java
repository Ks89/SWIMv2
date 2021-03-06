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

package it.swim.servlet.admin;

import it.swim.util.AdminCollegatoUtil;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import sessionBeans.localInterfaces.GestioneProposteLocal;

import entityBeans.Abilita;
import entityBeans.PropostaAbilita;
import exceptions.ProposteException;

/**
 * Servlet implementation class DettaglioPropostaServlet
 */
@Slf4j
public class DettaglioPropostaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestioneProposteLocal gestioneProposte;

	private Long idProposta;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DettaglioPropostaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("Entrato in dettaglio proposta abilita");

		idProposta = Long.parseLong(request.getParameter("idProposta"));

		log.debug("Entrato in dettaglio proposta abilita con id: " + idProposta);

		// ottengo l'email dell'admin collegato dalla sessione, appoggiandomi
		// ad una classe di utilita'
		String emailAdminCollegato = (String) AdminCollegatoUtil.getEmailAdminCollegato(request);

		// se e' null e' perche' l'admin non e' collegato e allora devo fare il
		// redirect alla homeadmin
		if (emailAdminCollegato == null) {
			response.sendRedirect("../admin");
			return;
		}


		this.gestisciProposta(request, response, idProposta);

		getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/dettaglioProposta.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doPost del dettaglio proposta");

		String confermaHiddenInput = request.getParameter("conferma");
		
		if(confermaHiddenInput!=null && confermaHiddenInput.equals("CONFERMA")) {


			String emailAdminCollegato = (String) AdminCollegatoUtil.getEmailAdminCollegato(request);

			Long idPropostaAbilita = Long.parseLong(request.getParameter("idProposta"));
			String nuovoNomeAbilitaProposta = request.getParameter("nuovoNomeAbilitaProposta");
			String descrizioneAbilita = request.getParameter("descrizioneAbilitaProposta");

			if((request.getParameter("tipo"))!=null && request.getParameter("tipo").equals("AGGIUNGI")) {

				if(nuovoNomeAbilitaProposta!=null && nuovoNomeAbilitaProposta.equals("")) {
					this.gestisciProposta(request, response, idProposta);

					request.setAttribute("erroreInserisciNomeAbilita", "Inserisci il nome dell'abilita'");
					getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/dettaglioProposta.jsp").forward(request, response);
					return;
				}

				try {
					Abilita abilitaInserita = gestioneProposte.confermaPropostaAbilitaSpecificandoAttributi(emailAdminCollegato, idPropostaAbilita, nuovoNomeAbilitaProposta, descrizioneAbilita);
					if(abilitaInserita!=null) {
						log.debug("nuova abilita inserita correttamente: " + abilitaInserita.getNome());
						request.setAttribute("inserimentoPropostaCorretto", "Inserimento abilita' avvenuto con successo!");
					} else {
						request.setAttribute("erroreInserimentoPropostaFallito", "Errore inserimento nuova abilita'");
					}
				} catch (ProposteException e) {
					log.error(e.getMessage(), e);
					request.setAttribute("errorePropostaNonTrovata", "Errore! Proposta abilita' non trovata");
				}
			} else {
				//se ho premuto su RIFIUTA
				if((request.getParameter("tipo"))!=null && request.getParameter("tipo").equals("RIFIUTA")) {
					log.debug("rifutare la proposta");
					try {
						PropostaAbilita rifiutata = gestioneProposte.rifiutaPropostaAbilita(idPropostaAbilita);
						if(rifiutata!=null) {
							log.debug("proposta rifiutata");
							request.setAttribute("rifiutoPropostaCorretto", "Proposta rifiutata con successo!");
						} else {
							request.setAttribute("erroreRifiutoPropostaFallito", "Errore durante il rifiuto della proposta'");
						}
					} catch (ProposteException e) {
						log.error(e.getMessage(), e);
						request.setAttribute("errorePropostaNonTrovata", "Errore! Proposta abilita' non trovata");
					}
				}
			}
		} else {
			request.setAttribute("nonHaiConfermatoInvioForm", "Hai interrotto la procedura. Nessun dato e' stato inviato");
			this.gestisciProposta(request, response, idProposta);
			getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/dettaglioProposta.jsp").forward(request, response);
			return;
		}

		List<PropostaAbilita> proposteAbilita = gestioneProposte.getProposteAbilitaNonConfermate();
		request.setAttribute("proposte", proposteAbilita);
		
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/admin/adminpanelproposte.jsp").forward(request, response);
	}


	private void gestisciProposta(HttpServletRequest request, HttpServletResponse response, Long idProposta) {
		try {
			PropostaAbilita propostaAbilita = gestioneProposte.getPropostaAbilitaById(idProposta);
			if(propostaAbilita!=null) {
				request.setAttribute("idProposta", propostaAbilita.getId());
				request.setAttribute("emailProposta", propostaAbilita.getUtente().getEmail());
				request.setAttribute("abilitaProposta", propostaAbilita.getAbilitaProposta());
				request.setAttribute("motivazioneProposta", propostaAbilita.getMotivazione());
			} else {
				request.setAttribute("errorePropostaNonTrovata", "Errore! Proposta non trovata");
			}
		} catch (ProposteException e) {
			log.error(e.toString(), e);
			request.setAttribute("erroreSconosciutoProposta", "Errore sconosciuto! Proposta non trovata");
		}
	}
}
