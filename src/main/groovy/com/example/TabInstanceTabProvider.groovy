// src/main/groovy/com/example/TabInstanceTabProvider.groovy
package com.example

import com.morpheusdata.core.AbstractInstanceTabProvider
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Account
import com.morpheusdata.model.Instance
import com.morpheusdata.model.User
import com.morpheusdata.views.HTMLResponse
import com.morpheusdata.views.ViewModel

/**
 * Instance tab provider for Morpheus SDK 8.x
 * Renders a custom tab on the Instance details page.
 *
 * Template path it renders: resources/renderer/hbs/instanceTab.hbs
 */
class TabInstanceTabProvider extends AbstractInstanceTabProvider {

    protected final MorpheusContext morpheus
    protected final Plugin pluginRef

    TabInstanceTabProvider(Plugin plugin, MorpheusContext morpheus) {
        this.pluginRef = plugin
        this.morpheus = morpheus
    }

    /**
     * Build the HTML for this tab.
     */
    @Override
    HTMLResponse renderTemplate(Instance instance) {
        // Safely read plugin settings
        Map<String, Object> settings = morpheus.settings.getPluginSettings(pluginRef).blockingGet() ?: [:]
        String dbUser = (settings['dbUsername'] ?: 'N/A') as String

        // ✅ Use addAttribute (SDK 8.x way)
        ViewModel<Instance> model = new ViewModel<>()
        model.object = instance
        model.addAttribute('dbUsername', dbUser)
        model.addAttribute('instanceId', instance?.id)
        model.addAttribute('instanceName', instance?.name)

        // Render the handlebars template
        return getRenderer().renderTemplate('hbs/instanceTab', model)
    }

    /**
     * Show/hide tab for user.
     */
    @Override
    Boolean show(Instance instance, User user, Account account) {
        return true
    }

    // ---------- Required Metadata ----------
    @Override
    String getCode() { 'tab' }

    @Override
    String getName() { 'tab' }

    @Override
    MorpheusContext getMorpheus() { morpheus }

    @Override
    Plugin getPlugin() { pluginRef }
}
