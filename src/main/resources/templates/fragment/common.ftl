<#import "navbar.ftl" as n>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="/static/css/bootstrap.min.css">
        <title>Title</title>
    </head>
    <body>
    <@n.navbar/>
    <div class="container py-3 ">
        <#nested>
        <script src="/static/js/jquery-3.2.1.slim.min.js"></script>
        <script src="/static/js/popper.min.js"></script>
        <script src="/static/js/bootstrap.min.js"></script>
    </div>
    </body>
    </html>
</#macro>

<#macro log path isRegistorForm>
    <div class="border p-3">

        <script src="https://www.google.com/recaptcha/api.js" async defer></script>

        <form action="${path}" method="post">
            <div class="form-group row">
                <label for="exampleInputEmail1" class="col-sm-2">User name: </label>
                <div class="col-sm-3">
                    <input type="text" name="username"
                           class="form-control ${(usernameError??)?string('is-invalid', '')}"
                           value="<#if user??>${user.username}</#if>"
                           id="exampleInputEmail1">
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
            </div>
            <#if isRegistorForm>
            <div class="form-group row">
                <label for="exampleInputEmail2" class="col-sm-2">Email: </label>
                <div class="col-sm-3">
                    <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}" id="exampleInputEmail2" value="<#if user??>${user.email}</#if>">
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            </#if>
            <div class="form-group row">
                <label for="exampleInputPassword1" class="col-sm-2">Password: </label>
                <div class="col-sm-3 ">
                    <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" id="exampleInputPassword1">
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </div>
            <#if isRegistorForm>
                <div class="form-group row">
                    <label for="exampleInputPassword2" class="col-sm-2">Confirm password: </label>
                    <div class="col-sm-3 ">
                        <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" id="exampleInputPassword2">
                        <#if password2Error??>
                            <div class="invalid-feedback">
                                ${password2Error}
                            </div>
                        </#if>
                    </div>
                </div>
            </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if isRegistorForm>
            <div>
                <div class="g-recaptcha" data-sitekey="6LcHfMoUAAAAAPzJvcS8P9FllBA7KbasDYCcjioa"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
            </#if>

            <#if !isRegistorForm><a href="/registration">Registration</a></#if>

            <button type="submit" class="btn btn-primary"><#if isRegistorForm>Create<#else>Sign In</#if></button>
        </form>
    </div>
</#macro>


<#macro logout>
    <div>
        <form action="/logout" method="post">
            <button type="submit" class="btn btn-primary"><#if user??>Sign Out<#else>Log in</#if> </button>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
    </div>
</#macro>
