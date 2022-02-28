/**
 * @Author: Lawrence Li
 * Last Modified: Feb 10, 2022
 *
 * This servlet acts like a controller.
 * There are two views: index.jsp and result jsp.
 * It decides between the two views. If user chooses
 * a breed from the index.jsp, the controller to go to result.jsp and shows
 * the breed's information. After showing the breed's information,
 * user can go back to index.jsp to choose a new breed.
 * The model is provided by DogFinderModel
 */


package ds.project1task2;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /getADog
 * then the servlet with the name DogFinderServlet should be used.
 */

@WebServlet(name = "findADog", urlPatterns = {"/getADog"})
public class DogFinderServlet extends HttpServlet {
    DogFinderModel dfm = null; // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    public void init() {
        dfm = new DogFinderModel();
    }


    // This servlet will reply to HTTP GET requests via this doGet method
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nextView;
        // Find the dog's breed from user input
        String breed = request.getParameter("dog-breeds");

        // use model to do the search and put the dog's information in request
        String[] breedInfo = dfm.doDogSearch(breed);

        /*
         * Here the input breed's data that is passed to the view
         * after it is returned from the model doDogSearch method.
         */

        request.setAttribute("friendly",breedInfo[0]);
        request.setAttribute("intelligence",breedInfo[1]);
        request.setAttribute("height",breedInfo[2]);
        request.setAttribute("weight",breedInfo[3]);
        request.setAttribute("lifespan",breedInfo[4]);
        request.setAttribute("picture",breedInfo[5]);

        nextView = "result.jsp";

        // Transfer control over to result.jsp
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);

    }

    public void destroy() {
    }

}
