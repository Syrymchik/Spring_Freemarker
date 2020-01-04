<#import "fragment/common.ftl" as frag>


<@frag.page>

<#--    ${mess?ifExists}-->
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if mess?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-${messType}" role="alert">
            ${mess}
        </div>
    </#if>

    <@frag.log "/login" false>

    </@frag.log>

</@frag.page>

