<#import "fragment/common.ftl" as frag>

<@frag.page>

    <div>
        <form action="/filter" method="post" class="form-inline my-3">
            <input class="form-control" type="text" name="filter" value="${filter?ifExists}">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" class="btn btn-primary ml-2">Filter</button>
        </form>
    </div>


    <#include "fragment/messageEdit.ftl" />
    <#include "fragment/messageList.ftl" />
</@frag.page>
