(function(window,document){var rootElement=document.documentElement;var colorSchemaStorageKey='Fluid_Color_Scheme';var colorSchemaMediaQueryKey='--color-mode';var userColorSchemaAttributeName='data-user-color-scheme';var defaultColorSchemaAttributeName='data-default-color-scheme';var colorToggleButtonName='color-toggle-btn';var colorToggleIconName='color-toggle-icon';function setLS(k,v){try{localStorage.setItem(k,v);}catch(e){}}
    function removeLS(k){try{localStorage.removeItem(k);}catch(e){}}
    function getLS(k){try{return localStorage.getItem(k);}catch(e){return null;}}
    function getSchemaFromHTML(){var res=rootElement.getAttribute(defaultColorSchemaAttributeName);if(typeof res==='string'){return res.replace(/["'\s]/g,'');}
        return null;}
    function getSchemaFromCSSMediaQuery(){var res=getComputedStyle(rootElement).getPropertyValue(colorSchemaMediaQueryKey);if(typeof res==='string'){return res.replace(/["'\s]/g,'');}
        return null;}
    function resetSchemaAttributeAndLS(){rootElement.setAttribute(userColorSchemaAttributeName,getDefaultColorSchema());removeLS(colorSchemaStorageKey);}
    var validColorSchemaKeys={dark:true,light:true};function getDefaultColorSchema(){var schema=getSchemaFromHTML();if(validColorSchemaKeys[schema]){return schema;}
        schema=getSchemaFromCSSMediaQuery();if(validColorSchemaKeys[schema]){return schema;}
        var hours=new Date().getHours();if(hours>=18||(hours>=0&&hours<=6)){return 'dark';}
        return 'light';}
    function applyCustomColorSchemaSettings(schema){var current=schema||getLS(colorSchemaStorageKey)||getDefaultColorSchema();if(current===getDefaultColorSchema()){resetSchemaAttributeAndLS();}else if(validColorSchemaKeys[current]){rootElement.setAttribute(userColorSchemaAttributeName,current);}else{resetSchemaAttributeAndLS();return;}
        setButtonIcon(current);setApplications(current);}
    var invertColorSchemaObj={dark:'light',light:'dark'};function toggleCustomColorSchema(){var currentSetting=getLS(colorSchemaStorageKey);if(validColorSchemaKeys[currentSetting]){currentSetting=invertColorSchemaObj[currentSetting];}else if(currentSetting===null){var iconElement=document.getElementById(colorToggleIconName);if(iconElement){currentSetting=iconElement.getAttribute('data');}
        if(!iconElement||!validColorSchemaKeys[currentSetting]){currentSetting=invertColorSchemaObj[getSchemaFromCSSMediaQuery()];}}else{return;}
        setLS(colorSchemaStorageKey,currentSetting);return currentSetting;}
    function setButtonIcon(schema){if(validColorSchemaKeys[schema]){var icon='icon-dark';if(schema){icon='icon-'+invertColorSchemaObj[schema];}
        var iconElement=document.getElementById(colorToggleIconName);if(iconElement){iconElement.setAttribute('class','iconfont '+icon);iconElement.setAttribute('data',invertColorSchemaObj[schema]);}else{Fluid.utils.waitElementLoaded(colorToggleIconName,function(){var iconElement=document.getElementById(colorToggleIconName);if(iconElement){iconElement.setAttribute('class','iconfont '+icon);iconElement.setAttribute('data',invertColorSchemaObj[schema]);}});}}}
    function setApplications(schema){if(window.REMARK42){window.REMARK42.changeTheme(schema);}
        var utterances=document.querySelector('iframe');if(utterances){var theme=window.UtterancesThemeLight;if(schema==='dark'){theme=window.UtterancesThemeDark;}
            const message={type:'set-theme',theme:theme};utterances.contentWindow.postMessage(message,'https://utteranc.es');}}
    applyCustomColorSchemaSettings();Fluid.utils.waitElementLoaded(colorToggleButtonName,function(){applyCustomColorSchemaSettings();var button=document.getElementById(colorToggleButtonName);if(button){button.addEventListener('click',()=>{applyCustomColorSchemaSettings(toggleCustomColorSchema());});}});})(window,document);