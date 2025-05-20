from nba_api.stats.endpoints import leagueleaders, leaguedashplayerstats
import pandas as pd
import streamlit as st

st.set_page_config(page_title="NBA Stats Explorer", layout="wide")

# Sidebar controls
season = st.sidebar.selectbox("Season", ["2024-25", "2023-24", "2022-23"])
mode = st.sidebar.radio("Stat Type", ["Total", "Average"])
stat_category = st.sidebar.selectbox("Stat", [
    "PTS", "AST", "REB", "STL", "BLK", "TOV", "FG_PCT",
    "FG3_PCT", "FT_PCT", "PLUS_MINUS", "MIN", "EFF", "GP"
])

@st.cache_data(show_spinner=True)
def load_data(season, stat_category):
    totals = leagueleaders.LeagueLeaders(
        stat_category_abbreviation=stat_category,
        season=season
    ).get_data_frames()[0]

    avgs = leaguedashplayerstats.LeagueDashPlayerStats(
        per_mode_detailed='PerGame',
        season=season
    ).get_data_frames()[0]

    avgs = avgs.rename(columns={
        'PLAYER_NAME': 'PLAYER',
        'TEAM_ABBREVIATION': 'TEAM'
    })

    return totals, avgs

totals_df, averages_df = load_data(season, stat_category)
current_df = averages_df if mode.lower() == "average" else totals_df
current_df = current_df.sort_values(by=stat_category, ascending=False)
current_df.index = range(1, len(current_df) + 1)

st.title("🏀 NBA Stats Explorer")
st.subheader(f"Top 10 Players - {mode} {stat_category} ({season})")

st.dataframe(current_df[['PLAYER', 'TEAM', stat_category]].head(10), use_container_width=True)