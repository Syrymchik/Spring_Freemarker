<#import "fragment/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    ${mess?ifExists}
    <div class="border p-3">
        <form action="/user/profile" method="post">
            <div class="form-group row">
                <label for="exampleInputEmail1" class="col-sm-2">Email: </label>
                <div class="col-sm-3">
                    <input type="email" name="email" class="form-control" id="exampleInputEmail1" value="${email!''}">
                </div>
            </div>
            <div class="form-group row">
                <label for="exampleInputPassword1" class="col-sm-2">Password: </label>
                <div class="col-sm-3 ">
                    <input type="password" name="password" class="form-control" id="exampleInputPassword1">
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit" class="btn btn-primary">Edit</button>
        </form>
    </div>
</@c.page>