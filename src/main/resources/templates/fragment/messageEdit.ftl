<a class="btn btn-primary mb-3" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Editer Messages
</a>

<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <form method="post" enctype="multipart/form-data"  class="form-row">
        <div class="col-md-3 mb-3">
            <input class="form-control mb-2 mr-sm-2 ${(textError??)?string('is-invalid', '')}"
                   type="text" name="text" placeholder="Введите сообщение" value="<#if message??>${message.text}</#if>">
            <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
            </#if>
        </div>
        <div class="col-md-3 mb-3">
            <input class="form-control mb-2 mr-sm-2 ${(tagError??)?string('is-invalid', '')}"
                   type="text" name="tag" placeholder="Введите тэг" value="<#if message??>${message.tag}</#if>">
            <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
            </#if>
        </div>
        <div class="col-md-3 mb-3">
            <input class="btn btn-warning ml-2" type="file" name="file" placeholder="Выберите файл">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />
        <div class="col-md-3 mb-3">
            <button class="btn btn-success ml-2" type="submit">Save Message</button>
        </div>
    </form>
</div>
