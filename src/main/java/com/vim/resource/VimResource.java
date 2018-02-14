package com.vim.resource;

import com.vim.domain.Image;
import com.vim.domain.Resource;
import com.vim.service.VimService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Path("v1.0")
public class VimResource {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Autowired
    private VimService vimService;


    // 接收镜像文件
    @POST
    @Path("images/receive")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Image receive() throws Exception {
        //在项目路径下设置imageFiles文件夹作为路径
        String upload_file_path = request.getSession().getServletContext().getRealPath("/") + "imageFiles/";

        if (!Paths.get(upload_file_path).toFile().exists()) {
            Paths.get(upload_file_path).toFile().mkdirs();
        }
        System.out.println(upload_file_path);
        // 设置工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置文件存储位置
        factory.setRepository(Paths.get(upload_file_path).toFile());
        // 设置大小，如果文件小于设置大小的话，放入内存中，如果大于的话则放入磁盘中,单位是byte
        factory.setSizeThreshold(0);

        ServletFileUpload upload = new ServletFileUpload(factory);
        // 中文文件名处理
        upload.setHeaderEncoding("utf-8");
        String fileName = new String();
        String imageId = new String();
        String imageName = new String();
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            if (item.isFormField()) {
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                if (name.equals("id"))
                    imageId = value;
                else if (name.equals("name"))
                    imageName = value;
                item.delete();
                request.setAttribute(name, value);
            } else {
                String name = item.getFieldName();
                String value = item.getName();
                System.out.println(name);
                System.out.println(value);

                fileName = Paths.get(value).getFileName().toString();
                request.setAttribute(name, fileName);
                // 写文件到path目录，文件名问filename
                item.write(new File(upload_file_path, fileName));
            }
        }
        Image image = new Image(imageId, imageName, upload_file_path + fileName);
        return vimService.saveImage(image);
    }

    @POST
    @Path("resources/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource createResource(@QueryParam("memory") final String memory, @QueryParam("storage") final String storage) {
        return vimService.createResource(memory, storage);
    }


    @POST
    @Path("resources/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteResource(@QueryParam("resourceId") final String resourceId) {
        if (vimService.deleteResource(resourceId)) {
            return "Successed!";
        } else
            return "Failed!";
    }

//    //可接收客户端上传文件
//    @POST
//    @Path("upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.TEXT_PLAIN)
//    public String upLoad() throws Exception {
//        //在项目路径下设置upload文件夹作为路径
//        String upload_file_path = request.getSession().getServletContext().getRealPath("/") + "upload/";
//        if (!Paths.get(upload_file_path).toFile().exists()) {
//            Paths.get(upload_file_path).toFile().mkdirs();
//        }
//        System.out.println(upload_file_path);
//        // 设置工厂
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // 设置文件存储位置
//        factory.setRepository(Paths.get(upload_file_path).toFile());
//        // 设置大小，如果文件小于设置大小的话，放入内存中，如果大于的话则放入磁盘中,单位是byte
//        factory.setSizeThreshold(0);
//
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        // 这里就是中文文件名处理的代码，其实只有一行
//        upload.setHeaderEncoding("utf-8");
//        String fileName ;
//        List<FileItem> list = upload.parseRequest(request);
//        for (FileItem item : list) {
//            if (item.isFormField()) {
//                String name = item.getFieldName();
//                String value = item.getString("utf-8");
//                System.out.println(name);
//                System.out.println(value);
//                request.setAttribute(name, value);
//            } else {
//                String name = item.getFieldName();
//                String value = item.getName();
//                System.out.println(name);
//                System.out.println(value);
//
//                fileName = Paths.get(value).getFileName().toString();
//                request.setAttribute(name, fileName);
//                // 写文件到path目录，文件名问filename
//                item.write(new File(upload_file_path, fileName));
//            }
//        }
//        return "success";
//    }

    @DELETE
    @Path("deleteVnfm")
    @Produces(MediaType.TEXT_PLAIN)
    public String delete() {
        if (vimService.deleteVnfm()) {
            return "Successed!";
        } else {
            return "Failed!";
        }
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getB() {
        return "dfilajklgajlfkfj";
    }

    @GET
    @Path("bb")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBb() {
        return "nnnnnn";
    }


}