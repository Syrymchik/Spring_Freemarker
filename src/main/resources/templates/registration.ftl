<#import "fragment/common.ftl" as frag>

<@frag.page>
    Registration Page
    ${mess?ifExists}
    <br>

    <@frag.log "/registration" true/>
    <a href="/login">Login</a>
</@frag.page>

