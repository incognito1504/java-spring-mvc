<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">
                    <title>Document</title>
                </head>

                <body>
                    <div class="container">
                        <div class="row">
                            <div class="col-12 mt-5">
                                <div class="alert alert-danger" id="alert">
                                    Bạn không có quyền truy cập nguồn tài nguyên này
                                </div>
                                <a href="/" class="btn btn-success">Trang Chủ</a>
                            </div>
                        </div>
                    </div>
                </body>

                </html>