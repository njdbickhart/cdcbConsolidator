# Conversion commands

The UI file was generated using QT designer in Anaconda. To invoke, type this in an anaconda shell:

```bash
designer
```

After saving the UI file, it can be converted to a python script using pyuic5. In the same anaconda shell, type:

```bash
pyuic5 -x cdcb_framework.ui -o mainwindow.py
```