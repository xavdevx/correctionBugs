/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctrl;

import com.modele.Identifiants;
import com.modele.Employe;
import com.modele.EmployeSB;
import com.utils.EmployesConstantes;
import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jacques
 */
public class Controleur extends HttpServlet {

    // Suite à la migration vers JPA plus besoin de ces attributs
//    private String dbUrl;
//    private String dbUser;
//    private String dbPwd;
//    private InputStream input;
//    private Properties prop;
//    private ClassLoader classLoader;
//    private static Connection conn;
//    IdentifiantsBean userBean;
//    ResultSet rs;
//    EmployesPersistance employeBD = new EmployesPersistance();
//    IdentifiantsPersistance utilisateurBD = new IdentifiantsPersistance();
    ArrayList<Employe> listeEmployes;
    ArrayList<Identifiants> listeCredentials;
    Employe employe;
    Employe employe1;
    Employe employe2;
    Employe employe3;
    Employe employe4;
    Employe employe5;
    Identifiants user;
    String idEmploye = EmployesConstantes.FRM_ID_EMPL_SELECT;

    @EJB
    private EmployeSB emEmploye;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String loginForm = request.getParameter(EmployesConstantes.FRM_LOGIN);
        String mdpForm = request.getParameter(EmployesConstantes.FRM_MDP);
        String action = request.getParameter(EmployesConstantes.ACTION);
        listeEmployes = new ArrayList<>();
        listeCredentials = new ArrayList<>();

        if (action == null) {
            request.getRequestDispatcher(EmployesConstantes.PAGE_INDEX).forward(request, response);
        } else {
            switch (action) {
                case EmployesConstantes.ACTION_LOGIN:
                    //Si le nom d'utilisateur et le mot de passe sont vide, renvoyer vers l'index
                    //avec un message d'erreur.
                    if (loginForm != null && mdpForm != null) {

                        if (loginForm.isEmpty() || mdpForm.isEmpty()) {
                            request.setAttribute("cleMessageErreur", EmployesConstantes.ERREUR_SAISIE_VIDE);
                            request.getRequestDispatcher(EmployesConstantes.PAGE_INDEX).forward(request, response);
                        } else {
//                            try {
                            //Récupération des utilisateurs
//                                rs = utilisateurBD.getIdentifiantss();
//                                //Récupération du login et du mot de passe
//                                while (rs.next()) {
//                                    userBean.setLogin(rs.getString("LOGIN"));
//                                    userBean.setMdp(rs.getString("MDP"));
//                                }
//                            } catch (SQLException ex) {
//                                System.out.println(ex.getMessage());
//                            }
                            listeCredentials.clear();
                            listeCredentials.addAll(emEmploye.getIdentifiants());

                            for (int i = 0; i < listeCredentials.size(); i++) {
                                ArrayList<Identifiants> userBase = listeCredentials;
                                //                            for (Identifiants userBase : listeCredentials) {
                                //Si le login et le mot de passe entrées correspondent aux login et mot
                                //de passe présents en base, créer un utilisateur et l'envoyer
                                //vers la page de tableauEmployes.
                                if (userBase.get(i).getLogin().equals(loginForm) && userBase.get(i).getMdp().equals(mdpForm)) {
                                    request.getRequestDispatcher(EmployesConstantes.PAGE_BIENVENUE).forward(request, response);
                                    //listeEmployes = employeBD.getListeEmployes();
//                                    listeEmployes.clear();
//                                    listeEmployes.addAll(emEmploye.getEmployes());
//                                    //request.setAttribute("cleListeEmployes", listeEmployes);
//                                    request.setAttribute("cleListeEmployes", listeEmployes);
//                                    request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);

                                } //Sinon envoyer vers la page d'accueil avec un message d'erreur.
                            }
                            request.setAttribute("cleMessageErreur", EmployesConstantes.ERREUR_INFOS_CONN_KO);
                            request.getRequestDispatcher(EmployesConstantes.PAGE_INDEX).forward(request, response);

                        }
                    }
                    break;
  

                case EmployesConstantes.ACTION_SUPPRIMER:
                    if (request.getParameter(idEmploye) != null) {
                        //int idClientASupprimer = Integer.parseInt(request.getParameter(idEmploye));

                        emEmploye.supprimerEmploye(request.getParameter(idEmploye));

                        //employeBD.supprimerEmployeParId(idClientASupprimer);
                        //listeEmployes = employeBD.getListeEmployes();
                        listeEmployes.clear();
                        listeEmployes.addAll(emEmploye.getEmployes());
                        request.setAttribute("cleListeEmployes", listeEmployes);
                        request.setAttribute("CLE_TEST_SUPPRIMER_SUCCES", EmployesConstantes.SUCCES_SUPPR);
                        request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);
                    } else {

                        listeEmployes.clear();
                        listeEmployes.addAll(emEmploye.getEmployes());
                        request.setAttribute("cleListeEmployes", listeEmployes);
                        request.setAttribute("CLE_TEST_SUPPRIMER_ECHEC", EmployesConstantes.ERREUR_SUPPR);
                        request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);
                    }
                    break;

                case EmployesConstantes.ACTION_MODIFIER:
                    
                    //employe = (EmployeBean) session.getAttribute("employe");
                    int idEmp = (Integer) session.getAttribute("idEmp");

                    employe = new Employe(idEmp,
                            request.getParameter(EmployesConstantes.CHAMP_NOM),
                            request.getParameter(EmployesConstantes.CHAMP_PRENOM),
                            request.getParameter(EmployesConstantes.CHAMP_TELDOMICILE),
                            request.getParameter(EmployesConstantes.CHAMP_TELPORTABLE),
                            request.getParameter(EmployesConstantes.CHAMP_TELPRO),
                            request.getParameter(EmployesConstantes.CHAMP_ADRESSE),
                            request.getParameter(EmployesConstantes.CHAMP_CODEPOSTAL),
                            request.getParameter(EmployesConstantes.CHAMP_VILLE),
                            request.getParameter(EmployesConstantes.CHAMP_EMAIL));

                    //employeBD.modifierEmploye(employe);
                    emEmploye.modifierEmploye(employe);

                    //listeEmployes = employeBD.getListeEmployes();
                    listeEmployes.clear();
                    listeEmployes.addAll(emEmploye.getEmployes());
                    request.setAttribute("cleListeEmployes", listeEmployes);

                    session.setAttribute("employe", employe);
                    request.getRequestDispatcher(EmployesConstantes.PAGE_DETAIL_EMPLOYE).forward(request, response);

//                    request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);
                    break;
//                    

                case EmployesConstantes.ACTION_DETAILS:
                    if (request.getParameter(idEmploye) != null) {
                        int idEmployeSelect = Integer.parseInt(request.getParameter(idEmploye));
                        session.setAttribute("idEmp", idEmployeSelect);
                        employe = emEmploye.getEmployeParId(idEmployeSelect);

                        session.setAttribute("employe", employe);
                        request.getRequestDispatcher(EmployesConstantes.PAGE_DETAIL_EMPLOYE).forward(request, response);

                    }
                    break;

                case EmployesConstantes.ACTION_VOIR_LISTE:
                    // listeEmployes = employeBD.getListeEmployes();
                    listeEmployes.clear();
                    listeEmployes.addAll(emEmploye.getEmployes());
                    request.setAttribute("cleListeEmployes", listeEmployes);
                    request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);
                    break;

                case EmployesConstantes.ACTION_AJOUTER:
                    request.getRequestDispatcher(EmployesConstantes.PAGE_AJOUT_EMPLOYE).forward(request, response);
                    break;

                case EmployesConstantes.ACTION_ADD_EMPLOYE:

                    employe = new Employe(
                            request.getParameter(EmployesConstantes.CHAMP_NOM),
                            request.getParameter(EmployesConstantes.CHAMP_PRENOM),
                            request.getParameter(EmployesConstantes.CHAMP_TELDOMICILE),
                            request.getParameter(EmployesConstantes.CHAMP_TELPORTABLE),
                            request.getParameter(EmployesConstantes.CHAMP_TELPRO),
                            request.getParameter(EmployesConstantes.CHAMP_ADRESSE),
                            request.getParameter(EmployesConstantes.CHAMP_CODEPOSTAL),
                            request.getParameter(EmployesConstantes.CHAMP_VILLE),
                            request.getParameter(EmployesConstantes.CHAMP_EMAIL));

                    emEmploye.ajouterEmploye(employe);

                    listeEmployes.clear();
                    listeEmployes.addAll(emEmploye.getEmployes());
                    request.setAttribute("cleListeEmployes", listeEmployes);
                    request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);
                    break;

                case EmployesConstantes.ACTION_CREER_EMPLOYES:
 
                    emEmploye.recruterEmployes(employe1);


                    listeEmployes.clear();
                    listeEmployes.addAll(emEmploye.getEmployes());
                    request.setAttribute("cleListeEmployes", listeEmployes);
                    request.getRequestDispatcher(EmployesConstantes.PAGE_TOUS_LES_EMPLOYES).forward(request, response);
                    break;

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() {

    }

}
