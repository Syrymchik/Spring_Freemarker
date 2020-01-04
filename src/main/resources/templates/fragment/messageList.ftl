<#include "security.ftl">

<div>Список сообщений</div>
<div class="card-columns">
    <#list messages as mess>
        <div class="card m-1">
            <#if mess.filename??>
                <img class="card-img-top" alt="Card image cap" src="/img/${mess.filename}">
            </#if>
            <div class="card-body">
                <h5 class="card-title">#${mess.tag}</h5>
                <div class="card-footer text-muted">
                    <a href="/user-messages/${mess.author.id}">${mess.authorName}</a>
                    <#if mess.author.id == currentUserId>
                        <a class="btn btn-primary" href="/user-messages/${mess.author.id}?message=${mess.id}">Edit</a>
                    </#if>
                </div>
                <p class="card-title">${mess.filename?ifExists}</p>
                <p class="card-text">${mess.text}</p>
            </div>
        </div>
    <#else >
        No messages
    </#list>
</div>