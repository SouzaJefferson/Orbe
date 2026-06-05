package controller;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/api/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Part filePart = request.getPart("foto");
            if (filePart == null || filePart.getSize() == 0) {
                response.getWriter().write("{\"url\": \"\"}"); // Retorna vazio se não tiver foto
                return;
            }

            String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
            // Salva na pasta 'uploads' dentro do servidor do Tomcat
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            filePart.write(uploadPath + File.separator + fileName);

            // Devolve o caminho para o front-end salvar no banco
            response.getWriter().write("{\"url\": \"../uploads/" + fileName + "\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\": \"Falha no upload.\"}");
            e.printStackTrace();
        }
    }
}