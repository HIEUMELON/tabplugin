// src/main/groovy/com/example/TabPlugin.groovy
package com.example

import com.morpheusdata.core.Plugin
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.model.Permission
import com.morpheusdata.model.OptionType
import com.morpheusdata.views.HandlebarsRenderer

class TabPlugin extends Plugin {
  @Override
  String getVersion() { '0.1.2' }

  @Override
  String getCode() { 'tab-plugin' }

  @Override
  void initialize() {
    this.setName('Tab Plugin')
    this.setDescription('Custom instance tab (example)')
    this.setAuthor('HIEUMELON')

    this.setRenderer(new HandlebarsRenderer(this.classLoader))

    // Register provider
    def provider = new TabInstanceTabProvider(this, morpheus)
    this.pluginProviders.put(provider.code, provider)

    // Plugin settings form
    this.settings << new OptionType(
      name: 'DB Username',
      code: 'tab-db-username',
      fieldName: 'dbUsername',
      fieldLabel: 'DB Username',
      inputType: OptionType.InputType.TEXT,
      required: false,
      category: 'Plugin',
      optionSourceType: 'none',
      displayOrder: 0
    )

    // Permissions
    this.setPermissions([
      Permission.build('Tab Plugin', 'tab-plugin',
        [Permission.AccessType.none, Permission.AccessType.full])
    ])
  }

  @Override
  void onDestroy() {
    // Optional cleanup
  }
}
