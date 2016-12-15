from django.contrib import admin

from models import Kategoria


class KategoriaAdminSelf(admin.ModelAdmin):
    list_display = ['nazov']
    search_fields = ['nazov']

admin.site.register(Kategoria, KategoriaAdminSelf)

