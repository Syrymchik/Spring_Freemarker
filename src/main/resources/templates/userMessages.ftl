<#import "fragment/common.ftl" as c>

<@c.page>
    <#if !isCurrentUser>
        <#if isSubscriber>
            <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Unsubscribe</a>
        <#else>
            <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe</a>
        </#if>
    </#if>
    <h3>${userChannel.username}</h3>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscriptions</div>
                        <div class="card-text">
                            <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Subscribers</div>
                        <div class="card-text">
                            <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#include "fragment/messageEdit.ftl" />
    </#if>
    <#include "fragment/messageList.ftl" />

</@c.page>