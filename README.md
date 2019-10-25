# cdcbConsolidator
---
A GUI tool to consolidate CDCB files. 

## Current version

Requirements: You must have java 1.8+ installed on your computer! You can find the package to install the java 8 development kit [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

#### Draft state one for early deployment (v 0.0.1)

The current state of the utility is the presence of one of three tabs for converting the flat files from the CDCB. This tab transposes the data from the ANIM and EVAL flat files into a horizontal format (each row == one animal) and uses a comma (",") delimiter in the output file. 

#### Future feature wishlist

I am planning to add two more tabs, with one tab allowing for simple, guided SQL searching of data present in these files and the other allowing for raw SQL input for advanced users. 

## Usage

Simply double-click the "jar" file to bring up the program window:

![GUI default look](https://lh3.googleusercontent.com/b9n4PhHrBwhahX-_MAKZlpPMPZyqMThE4vvTkmgO1B3_DOUk7W5fL6eYRG_yH9l1h71eCRo3vMD9fSEDvCM68fL0H-jNEFXiCOSQx2gpy4LvNya04pFbqo-o_JaexjWknC3Vo3BQPK8rnXJ18-24M4otj0auOvhM-6QVhAkwV-5vhK-nVZczSrW_gG713Jeus57nV0_PAn7_JN88MqZAa9Pl6jyWEyZ0pB28pgNipfYzL64sl7_eaQRIIjy14P_rV7kzx8Bddx951yxwgrOb_G_pycSRzjlZxzzKMaJ_AkeX4RU-7AiCX5Z2jECDDOvLZL1MebvI-fDcNbUQKrhm5-9cLyyP4eTVqxp3D8VFCknfA3R1WTG6aRwSADfLCj0Mxpewlo_GB-yPFRtLxf8yajS-1uMZ3Olv3fn8b0j4Q503ZU7OVgHKc0-dvIzuXg-XqR-QFiMk-0KEm0WgxtAYaGFJysCxUkEcCj6KcCdmdvz7t1xjM6OqLMtuFZEbvZmfETv6yMndBqYYVZQ-ZEsp0WCAg22ms0Jf84wZGQ06_cTE8ORIDX7UBcRQITTBYKJGMG3loBIaPafIK7IWx_OP7b9CyolQTYdqIpWm4C0njDJZG_3Fay5FseGXoeafG9kgDVg6p89WUJjKxCkbnzAbF_YVpvKzFACOBXGF7670jCk-o6AVJ-rJjg=w456-h429-no)

Right now, the program only uses the "Anim File" and "Eval File" fields for data conversion (that's basically all you need to emulate the old format!). To populate these fields, just click the "browse" button to the right of them to bring up a filed chooser:

![File Chooser Window](https://lh3.googleusercontent.com/eLLxeG2T8-cIIGR4_QQV8fqIDkVMxEFac_SpoJUuYnf4UvJ2cTAvz3AqPR6galh1SeS9y7K8Sy0eCeDaBvrJ9RbYXES4ZQz0doO7zBTI2BR7Y8uHV92sCu2elAaYo55Qsvc-tILjbbRChvfy0swsrGXrEv8KHhXTdK18nQ-HOnELBi17GKS_azXue2JQNiiEygHZ2VIJRQbqKRXm5kuma9LX4_YozxCakYE72dm56zcKVIBn42M1RxheLND5t7tB-KkkR7Hhz0TcBpHXWQlPzml1Za0LUwIy5br5anoovwb3dQ_VecYSfrNSe7Z5vDX6GehSoHnF4RMkGMXRykRHjMufU0G8rw6UGrsCgQHmNf6aNqmAfVNofjTnsH8p9PWeKBME9Q_TeL6lMobuIwcS32U5LuAs6JnoJbpoa2ab7P6Ne66IAGh3Ip_Jh9qKAHLPQVKgoHjTjZr7YT3jKtO_PHxBXz-c3vAdkZfN-UJDnMv11HebcSuSdZHy7N9eW6uFEm2_rgkP-MNPeW6ui_-_-_Z6sr8pO53DsiMmA65GF7GAjOj1jYLXBkbQHX96KwPcb6S-oSHQmzbEhcYVSY07E0G0NeoXIs6AqzOSY53-Ndbpe9DS_-dxSeCQ970LCnKpOFvw3mvDvp7McsVBqXWboQrAiWeHwJjKTLhxjU0cXXe7d9dzoMHJ4w=w821-h800-no)

The file chooser window selects ".csv" files by default, but you can display all files by changing the "Files of type" dropdown menu at the bottom of the window.

In order to run the conversion, you will also need to define a new "Output" file name in the bottom text field. You may use the Browse button to select the location and name the new file. If you enter the text in the text field directly, the file may end up in your "Documents" folder by accident, so it is highly advised that you select the location with the "Browse" button!

![Options entered and ready to go](https://lh3.googleusercontent.com/_NXyWUwB9pLgdu3X832FQ5npli_HUBiz7W7FZt4sGfPPQ-Z3Sszd8IMQNczGnUc0fGAnC9cbwjSGwMs0rNEJUrJ3KejNOLTZ1HxOuX_ZzWK9QTjsE9hNl97Hxdbk_SHWuVQlSE48CJaYeWQmiUWGHQV9bpsXkIeJCMGpPqz_5CxNkBHu02kH7PNhVLb_DqBgvvoi0YjIPSRd21yAYih9-hnOSiSLh12wQTwId8IOX-3wr-_Nn2ljCbyT2s9OzRgHbt4qtr-p6IjMwPkAx2Ct0Tw710v_RTTUMtvV1H_JID8zBobZ-0m9PJymZiLrz4ENb0I2VlduQfojeXfUcrSnAGeJCXmLNQNqAaku2lThN2_o7spsBP-ryWzBubf5tKybAO6zYAUPkYA7HAan9htOuNqpVwuJip1jWpXCQa6UvofZn6XbDUYPLlxwSC4hhd6xum0uJ7EOBWWKhcqhO-1m15rgN5H6vquzP8fnGkZ5TEgXDyLg-hwc_vksi683y1kfb12Hp846BjS7U7q3xSzuUkHnhXsFs_Il6CVJ4o3Hc0azcWIb95Hg9b8CZi5PruX9RZE7DtfNv0dulMdceOQ_3BkKkaveWrtCOz3W93PGgWw4KQvAwzusvm_Si5k8HR_BgK8fyZOniLNoKpXHcrImu5IW7M2swsdZQ5Z0h_nwZm4cbM50Am4dGw=w457-h424-no)

When you have all three text fields updated as listed in the image above, you are ready for the conversion! Just hit the "Convert" button and let the program do its magic. You can tell that the program has converted the files successfully if the progress bar turns orange as shown:

![Progress finished](https://lh3.googleusercontent.com/t_Q8a9BnS3gLWQ5PeUlm6bq-DdYnyH8n2pMaLUkRvhizQe7g0V-94j9nzCNizh1IHLLTocua80f2jWjGbfrmYUc-ocBWZDA0puNWUlR0Qz5XImpEpxyNai0qoOSqsPI-DSVlDO9nNEcHBs7mKjz8K2iz7Z_ajxFzbX9VXsJdGFXLd0zdywiq31pdOr1EgA38k1naMD8kzALwtDdob620FD5EGCNYzV2ebPKeug47MPozbySBYbhRpp86RhBLdFX2MNAr4pUrxGYNh7Z55mbrBr_eNhDVRag55C5K6rLJPpCoGO90oiti9HDkP_f37bk5sPa9zsP2I0lmtND7XEPvGTrQqNdaPtqCxiicw7TV_p0mOb54q5ziTMX-1-xLs0jbXjxjP4CO12cIPlPjNIUgDVr99J4WR393Hqb_yILedrXnmWLS8M_jtA6TzZkxU7FsEpTDiQKmU6gLTZ1niZYiUBxj1Wb2-BYSI5pgqqMgqvhrx25lAXqNzmRzXBX9I06gWuiclXRF2c9kBAcA7-iL5Y1IMi8KpIdT58PIiiVfnbVVAF_XNg4vzenDuurIKxJjPspTRLQYpPmjmGtc6YrI00qhusbFlz94lexaxigyZH02taVS8rmQD2Dr1NDZzUQeTuRIq0QjJ5xO0aN_yKr22VExggbIMFjoHJMTz8CJzv1bTV4nbzunxA=w458-h425-no)

To clear the text fields so that you can enter a new file, just hit the "Clear" button. 
