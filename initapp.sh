#!/bin/bash
#******************************************************************************
#    Email App Init Script
#******************************************************************************
#
# DESCRIPTION
#    Calls init methods
#
#
#==============================================================================
#   Begin
#==============================================================================


curl -k "https://emailapp.apps.pcfone.io/refreshtoken?token=6bea20b4ef1246f0aa2424ac185740c8-r"
curl -k "https://emailapp.apps.pcfone.io/accesstoken"
curl -k "https://emailapp.apps.pcfone.io/add?email=bpark@pivotal.io"
curl -k "https://emailapp.apps.pcfone.io/addproduct?email=bpark@pivotal.io&product=elastic-runtime"
curl -k "https://emailapp.apps.pcfone.io/addproduct?email=bpark@pivotal.io&product=ops-manager"
curl -k "https://emailapp.apps.pcfone.io/list"
curl -k "https://emailapp.apps.pcfone.io/listreleases?slug=elastic-runtime"
curl -k "https://emailapp.apps.pcfone.io/updatereleases?slug=elastic-runtime"
curl -k "https://emailapp.apps.pcfone.io/latestrelease?slug=elastic-runtime"
