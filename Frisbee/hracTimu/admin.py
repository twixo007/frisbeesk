from django.contrib import admin
import nested_admin
from models import HracTimu


class HracTimuAdminSelf(nested_admin.NestedAdmin):
    list_display = ['hrac','tim']
    search_fields = ['hrac', 'tim']
    

admin.site.register(HracTimu, HracTimuAdminSelf)


class HracTimuAdmin(nested_admin.NestedStackedInline):
    model = HracTimu
    extra = 0
    inline_classes = ('grp-collapse grp-open',)
