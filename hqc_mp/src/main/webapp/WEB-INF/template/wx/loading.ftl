[#-- Model Loading --]
<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="${loadingId}">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">[#if !showLoading??]正在载入...[#else]${showLoading}[/#if]</div>
        <div class="am-modal-bd">
            <span class="am-icon-spinner am-icon-spin"></span>
        </div>
    </div>
</div>