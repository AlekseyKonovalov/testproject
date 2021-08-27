package com.alekseykon.testproject.presentation.fragments.applicationsList

import com.alekseykon.testproject.domain.models.SearchItemEntity
import com.alekseykon.testproject.presentation.fragments.base.BaseAction

internal sealed class ItemsListAction : BaseAction {
    internal class ShowLoading(val isShow: Boolean) : ItemsListAction()
    internal class ShowSearchLoading(val isShow: Boolean) : ItemsListAction()
    internal class UpdateFields(val phone: String) : ItemsListAction()
    internal class ShowError(val errorText: String) : ItemsListAction()
    internal class UpdateItems(val items: List<SearchItemEntity>) : ItemsListAction()
}
